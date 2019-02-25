package src;

import java.sql.*;



public class TASDatabase {
    public static void main(String [] args){
       
        String badgeID = "12565C60";
        TASDatabase yeah = new TASDatabase();
        yeah.getBadge(badgeID);
        System.out.println();
        yeah.getPunch(147);
        yeah.getShiftByBadge(badgeID);
        
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
        
        int dbPunchID = resultset.getInt(1);
        int termID = resultset.getInt(2);
        String badgeID = resultset.getString(3);
        Timestamp ts = resultset.getTimestamp(4);
        long longTS = ts.getTime();
        int punchType = resultset.getInt(5);
        
        
        System.out.println(dbPunchID + " " + termID + " " + badgeID + " " + longTS + " " + punchType);
        }
        
        catch(Exception e){
            System.err.println(e.toString());
        }

        return null;
    }
    
    public String getShiftByID(int shift_num){
        

        try{
            query = "SELECT * FROM shift WHERE id = '" + shift_num + "'";
            pstSelect = conn.prepareStatement(query);
            pstSelect.execute();
            resultset = pstSelect.getResultSet();
        
            resultset.first();
            for(int i = 1; i < 11; i++){
            System.out.println(resultset.getString(i));
            }
        }
        
        catch(Exception e){
            System.err.println(e.toString());
        }

        return null;
    }
    
    public String getShiftByBadge(String b){
        
        try{
            query = "SELECT * FROM employee WHERE badgeid = '" + b + "'";
            pstSelect = conn.prepareStatement(query);
            pstSelect.execute();
            resultset = pstSelect.getResultSet();
        
            resultset.first();
             
            int shift_id = Integer.parseInt(resultset.getString("shiftid"));
            this.getShiftByID(shift_id);
            
            
        }
        
        catch(Exception e){
            System.err.println(e.toString());
        }
        
        return null;
    } 

}
