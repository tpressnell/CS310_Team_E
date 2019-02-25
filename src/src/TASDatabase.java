package src;

import java.sql.*;



public class TASDatabase {
    PreparedStatement pstSelect, pstUpdate;
    ResultSet resultset;
    ResultSetMetaData metadata;
    String query;
    Connection conn;
  
    
    public static void main(String [] args){
        
    }
    public TASDatabase(){
        
        
        
           try{
               
           
            String server = ("jdbc:mysql://localhost/tas");
            String username = "TeamE";
            String password = "TeamE123!";
            System.out.println("Connecting to " + server + "...");
             
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            
            conn = DriverManager.getConnection(server, username, password);
            
            if(conn.isValid(0))
                System.out.println("Connection Successful");
           
           }
           
            catch (Exception e) {
                System.err.println(e.toString());
            }
    }
        
        
    
    
    public Badge getBadge(String badgeID) throws SQLException{
        query = "SELECT * FROM tas.badge WHERE id = '" + badgeID + "'";
        pstSelect = conn.prepareStatement(query);
        resultset = pstSelect.getResultSet();
        return null;
    }
    
    public Punch getPunch(int punchID){
        return null;
    }
    public Shift getShift(int shift_num){
        return null;
    }
    public Shift getShift(Badge b){
        return null;
    }
    
    
    
}