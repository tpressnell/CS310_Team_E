package src;

import java.sql.*;



public class TASDatabase {
    public static void main(String [] args){
       
        String badgeID = "12565C60";
        TASDatabase yeah = new TASDatabase();
        yeah.getBadge(badgeID);
        
    }
    
    Connection conn;
    PreparedStatement pstSelect, pstUpdate;
    ResultSet resultset;
    String query;
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

        catch (Exception e) {
            System.err.println(e.toString());
        }
   }

    public String getBadge(String badgeID){
        try{
            
        
        query = "SELECT * FROM badge WHERE id = '" + badgeID + "'";
        pstSelect = conn.prepareStatement(query);
        pstSelect.execute();
        resultset = pstSelect.getResultSet();
        
        resultset.first();
        String name = resultset.getString(2);
        String idNum = resultset.getString(1);
        System.out.println(name + " "  + idNum);
        
        }
        
        catch(Exception e){
            System.err.println(e.toString());
        }

        
        return null;
    }

    public String getPunch(int punchID){
        try{
            
        
        query = "SELECT * FROM punch WHERE id = '" + punchID + "'";
        pstSelect = conn.prepareStatement(query);
        pstSelect.execute();
        resultset = pstSelect.getResultSet();
        resultset.first();
        
        System.out.println(resultset.toString());
        }
        
        catch(Exception e){
            System.err.println(e.toString());
        }
        return null;
    }
    
    public Shift getShift(int shift_num){
        return null;
    }
    public Shift getShift(Badge b){
        return null;
    } 



    }