package src;

import java.sql.*;



public class TASDatabase {
    public static void main(String [] args){
        
    
        Connection conn;
        PreparedStatement pstSelect, pstUpdate;
        ResultSet resultset;
        ResultSetMetaData metadata;
        
           try{
               
           
            String server = ("jdbc:mysql://localhost/tas");
            String username = "TeamE";
            String password = "TeamE123!";
            System.out.println("Connecting to " + server + "...");
             
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            
            conn = DriverManager.getConnection(server, username, password);
            
            if(conn.isValid(0))
                System.out.println("Connection Successful");
            
            pstSelect = null;
            pstUpdate = null;
            resultset = null;
            metadata = null;
           }
           
            catch (Exception e) {
                System.err.println(e.toString());
            }
        
        
    }
    
    public Badge getBadge(String badgeID){
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