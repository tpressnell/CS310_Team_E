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
<<<<<<< HEAD
           catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
                System.err.println(e.toString());
           } 
=======
           
            catch (Exception e) {
                System.err.println(e.toString());
            }
        
        
    }
    
    public Badge getBadge(){
        
    }
    
    public Punch getPunch(){
        
    }
    public Shift getShift(){
        
    }
    public Shift getShift(){
        
>>>>>>> 2abd37da0fa5ae96c59ed62aeb742b36f0a22483
    }
    
    
}