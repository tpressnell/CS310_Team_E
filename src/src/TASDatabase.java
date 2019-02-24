package src;

import java.sql.*;



public class TASDatabase {
    
    Connection conn;
    PreparedStatement pstSelect, pstUpdate;
    ResultSet resultset;
    ResultSetMetaData metadata;
    
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
            
            pstSelect = null;
            pstUpdate = null;
            resultset = null;
            metadata = null;
           }
           catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
                System.err.println(e.toString());
           }
          
           
        
        
    }
}
