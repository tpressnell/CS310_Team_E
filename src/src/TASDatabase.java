package src;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;




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
        
        Badge b = new Badge(name, idNum);
        
        return b;
        }
        
        catch(Exception e){
            System.err.println("** getBadge: " + e.toString());
        }

        return null;
        
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
        String otStamp = ts.toString();
        
        Punch p = new Punch(b, longTS, punchType, otStamp, dbPunchID, termID);
        
        return p;
        }
        
        catch(Exception e){
            System.err.println("** getPunch: " + e.toString());
        }

        return null;
    }
    
    public Shift getShift(int shift_num){
        

        try{
            query = "SELECT * FROM shift WHERE id = '" + shift_num + "'";
            pstSelect = conn.prepareStatement(query);
            pstSelect.execute();
            resultset = pstSelect.getResultSet();
        
            resultset.first();
            int shiftID = resultset.getInt(1);
            
            Timestamp ts = resultset.getTimestamp(3);
            long startTime = ts.getTime();
             
            ts = resultset.getTimestamp(4);
            long endTime = ts.getTime();
               
            ts = resultset.getTimestamp(8);
            long lunch_start = ts.getTime();
              
            ts = resultset.getTimestamp(9);
            long lunch_end = ts.getTime();
            
            Shift returnShift = new Shift(startTime, endTime, shiftID, lunch_start, lunch_end);
            
            return returnShift;
        }
        
        catch(Exception e){
            System.err.println(e.toString());
        }

        return null;
    }
    
    public Shift getShift(Badge badge){
        
        try{
            query = "SELECT * FROM employee WHERE badgeid = '" + badge.getId() + "'";
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
            
            int shiftID = resultset.getInt(1);
           
            Timestamp ts = resultset.getTimestamp(3);
            long startTime = ts.getTime();
            
            ts = resultset.getTimestamp(4);
            long endTime = ts.getTime();
            
            ts = resultset.getTimestamp(8);
            long lunch_start = ts.getTime();
            
            ts = resultset.getTimestamp(9);
            long lunch_end = ts.getTime();
            
            Shift returnShift = new Shift(startTime, endTime, shiftID, lunch_start, lunch_end);
             
            return returnShift;
            
        }
        
        catch(Exception e){
            System.err.println(e.toString());
        }
        
        return null;
    } 
    public int insertPunch(Punch p){
        
        try {
            int punchTypeID, termId;
            String badgeID;

            // Time Paramterers
            String timeStamp = p.getStringTimestamp();

            // Badge Parameters
            badgeID = p.getBadgeID();
            
            // Punch/Terminal  Parameters 
            punchTypeID = p.getPunchType();
            termId = p.getTerminalid();
            
            

            // Put in in the database thingy
            query = "INSERT INTO punch (terminalid, badgeid, originaltimestamp, punchtypeid) VALUES (?, ?, ?, ?)";
            
            pstUpdate = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstUpdate.setInt(1, termId);
            pstUpdate.setString(2, badgeID);
            pstUpdate.setString(3, timeStamp);
            pstUpdate.setInt(4, punchTypeID);
                
                // Execute Update Query
                
            pstUpdate.executeUpdate(); 
            resultset = pstUpdate.getGeneratedKeys();
            resultset.first();
            
             return resultset.getInt(1);
        
        }
        catch(Exception e){
            System.err.println("** insertPunch: " + e.toString());
        }
        return 0;
    }
    
    public ArrayList getDailyPunchList(Badge b, long ts){
        //create ArrayList to hold punches for a given day
        try{
        ArrayList<Punch> punches = new ArrayList<>();
        String originalTimeStamp = new SimpleDateFormat("yyyy-MM-dd").format(ts);
        query = "SELECT * FROM punch WHERE badgeid = '" + b.getId() + "'" ;
        pstSelect = conn.prepareStatement(query);
        pstSelect.execute();
        resultset = pstSelect.getResultSet();
        resultset.first();
        
        while(resultset.next()){
            String timestamp = resultset.getString(4);
            if(timestamp.contains(originalTimeStamp)){
                int dbPunchID = resultset.getInt(1);
                int termID = resultset.getInt(2);
                String badgeID = resultset.getString(3);
                Timestamp dbTS = resultset.getTimestamp(4);
                long longTS = dbTS.getTime();
                int punchType = resultset.getInt(5);
                String otStamp = dbTS.toString();

                Punch p = new Punch(b, longTS, punchType, otStamp, dbPunchID, termID);

                punches.add(p);
                }
        }
        
        
        
        return punches;
        }
        
        catch(Exception e){
            System.err.println("** getDailyPunchList: " + e.toString());
        }
        
        
        return null;
    }
    
    public ArrayList getPayPeriodPunchList(Badge b, long ts){
        final long MILLIS_IN_DAY = 86400000;
        ArrayList<Punch> payPeriodPunches = new ArrayList<>();
        int daysBefore = 0;
        
        //Create greg object
        GregorianCalendar g = new GregorianCalendar();
        g.setTimeInMillis(ts);
        
        //Find day of week
        switch(g.get(Calendar.DAY_OF_WEEK)){
            case 1:
                daysBefore = 0;
                break;
            case 2:
                daysBefore = 1;
                break;
            case 3:
                daysBefore = 2;
                break;
            case 4:
                daysBefore = 3;
                break;
            case 5:
                daysBefore = 4;
                break;
            case 6:
                daysBefore = 5;
                break;
            case 7:
                daysBefore = 6;
                break;
        }
        
        //For each day in period, parse punches
        try{
            for (int i = 0; i < daysBefore; i++){
                String day = new SimpleDateFormat("yyyy-MM-dd").format(ts - (MILLIS_IN_DAY * i));

                query = "SELECT * FROM punch WHERE badgeid = '" + b.getId() + "'" ;
                pstSelect = conn.prepareStatement(query);
                pstSelect.execute();
                resultset = pstSelect.getResultSet();
                resultset.first();

                while(resultset.next()){
                    String timestamp = resultset.getString(4);
                    if(timestamp.contains(day)){
                        int dbPunchID = resultset.getInt(1);
                        int termID = resultset.getInt(2);
                        String badgeID = resultset.getString(3);
                        Timestamp dbTS = resultset.getTimestamp(4);
                        long longTS = dbTS.getTime();
                        int punchType = resultset.getInt(5);
                        String otStamp = dbTS.toString();

                        Punch p = new Punch(b, longTS, punchType, otStamp, dbPunchID, termID);

                        payPeriodPunches.add(p);
                    }
                }
                return payPeriodPunches;
            }
        }
        catch(Exception e){
            System.err.println(e.toString());
        }
        return null;
    }
}
