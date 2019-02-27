package src;

import java.sql.*;



public class TASDatabase {
    
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

    public Badge getBadge(String badgeID){
        try{
            
        
        query = "SELECT * FROM badge WHERE id = '" + badgeID + "'";
        pstSelect = conn.prepareStatement(query);
        pstSelect.execute();
        resultset = pstSelect.getResultSet();
        
        resultset.first();
        String idNum = resultset.getString(1);
        String name = resultset.getString(2);
        
        Badge b = new Badge(idNum, name);
        
        
        }
        
        catch(Exception e){
            System.err.println(e.toString());
        }

        return b;
        
    }

    public Punch getPunch(int punchID){
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
        Badge b = this.getBadge(badgeID);
        
        
        Punch p = new Punch(b, longTS, punchType);
        }
        
        catch(Exception e){
            System.err.println(e.toString());
        }

        return p;
    }
    
    public Shift getShift(int shift_num){
        

        try{
            query = "SELECT * FROM shift WHERE id = '" + shift_num + "'";
            pstSelect = conn.prepareStatement(query);
            pstSelect.execute();
            resultset = pstSelect.getResultSet();
        
            resultset.first();
            Timestamp ts = resultset.getTimestamp(3);
            long startTime = ts.getTime();
            
            ts = resultset.getTimestamp(4);
            long endTime = ts.getTime();
            
            Shift returnShift = new Shift(startTime, endTime);
            
            return returnShift;
        }
        
        catch(Exception e){
            System.err.println(e.toString());
        }

        return null;
    }
    
    public Shift getShift(String badgeID){
        
        try{
            query = "SELECT * FROM employee WHERE badgeid = '" + b + "'";
            pstSelect = conn.prepareStatement(query);
            pstSelect.execute();
            resultset = pstSelect.getResultSet();
        
            resultset.first();
             
            int shift_id = Integer.parseInt(resultset.getString("shiftid"));
            
            query = "SELECT * FROM shift WHERE id = '" + shift_id + "'";
            pstSelect = conn.prepareStatement(query);
            pstSelect.execute();
            resultset = pstSelect.getResultSet();
        
            resultset.first();
            
            Timestamp ts = resultset.getTimestamp(3);
            long startTime = ts.getTime();
            
            ts = resultset.getTimestamp(4);
            long endTime = ts.getTime();
            
            Shift returnShift = new Shift(startTime, endTime);
            
            return returnShift;
            
        }
        
        catch(Exception e){
            System.err.println(e.toString());
        }
        
        return null;
    } 

}
