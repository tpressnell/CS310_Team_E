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
            
            String description = resultset.getString(2);
            
            int dailyScheduleID = resultset.getInt(3);
            
            query = "SELECT * FROM dailyschedule WHERE id = '" + dailyScheduleID + "'";
            pstSelect = conn.prepareStatement(query);
            pstSelect.execute();
            resultset = pstSelect.getResultSet();
            resultset.first();
            
            Timestamp ts = resultset.getTimestamp(2);
            long start = ts.getTime();
             
            ts = resultset.getTimestamp(3);
            long stop = ts.getTime();
            
            int interval = resultset.getInt(4);
            int grace_period = resultset.getInt(5);
            int dock = resultset.getInt(6);
            
               
            ts = resultset.getTimestamp(7);
            long lunch_start = ts.getTime();
              
            ts = resultset.getTimestamp(8);
            long lunch_stop = ts.getTime();
            
            int lunch_deduct = resultset.getInt(9);
            
            DailySchedule defaultSchedule = new DailySchedule(start,stop,lunch_start,lunch_stop,interval,grace_period,dock,lunch_deduct);
            
            Shift returnShift = new Shift(shiftID, description, defaultSchedule);
            
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
    public Shift getShift( Badge b, long ts) {
        
        GregorianCalendar garry = TASLogic.makeCal(ts);
        
        boolean overrideFlag = false;
        
        try {
            
            // QUERY 1 
            
            query = "SELECT * FROM employee WHERE badgeid = ?";
            pstSelect = conn.prepareStatement(query);
            pstSelect.setString(1, b.getId());
            pstSelect.execute();
            resultset = pstSelect.getResultSet();
            resultset.first();
            
            int employeeShiftId = resultset.getInt(7); //"That gives us their shift id" -Tyler
            
            
            //QUERY 2
            
            query = "SELECT * FROM shift WHERE shiftid = ?";
            pstSelect = conn.prepareStatement(query);
            pstSelect.setInt(1, employeeShiftId);
            pstSelect.execute();
            resultset = pstSelect.getResultSet();
            resultset.first();
            
            String description = resultset.getString(2);
            int dailyScheduleId = resultset.getInt(3);
            
            //QUERY 3
            
            query = "SELECT * FROM dailyshedule WHERE id = ?";
            pstSelect = conn.prepareStatement(query);
            pstSelect.setInt(1, dailyScheduleId);
            pstSelect.execute();
            resultset = pstSelect.getResultSet();
            resultset.first();
            
            long start = resultset.getLong(2);
            long stop = resultset.getLong(3);
            long lunch_start = resultset.getLong(4);
            long lunch_stop = resultset.getLong(5);
            int interval = resultset.getInt(6);
            int grace_period = resultset.getInt(7);
            int dock = resultset.getInt(8);
            int lunchDeduct = resultset.getInt(9);
            
            DailySchedule dailyScheudle = new DailySchedule(start, stop, lunch_start, lunch_stop, interval, grace_period, dock, lunchDeduct);
            Timestamp Jenny = new Timestamp(garry.getTimeInMillis());
            
            
            // QUERY 4
            
            query = "SELECT * FROM scheduleoverride WHERE start = ? AND badegid = null";
            pstSelect = conn.prepareStatement(query);
            pstSelect.setTimestamp(1, Jenny);
            pstSelect.execute();
            resultset = pstSelect.getResultSet();
            resultset.first();
            
            
            if( resultset.next() == true) {
                
                overrideFlag = true;
                
                int newDailyScheduleId = resultset.getInt(6);
                
                // QUERY 4.5
                
                query = "SELECT * FROM dailyshedule WHERE id = ?";
                pstSelect = conn.prepareStatement(query);
                pstSelect.setInt(1, newDailyScheduleId);
                pstSelect.execute();
                resultset = pstSelect.getResultSet();
                resultset.first();

                long newStart = resultset.getLong(2);
                long newStop = resultset.getLong(3);
                long newLunch_start = resultset.getLong(4);
                long newLunch_stop = resultset.getLong(5);
                int newInterval = resultset.getInt(6);
                int newGrace_period = resultset.getInt(7);
                int newDock = resultset.getInt(8);
                int newLunchDeduct = resultset.getInt(9);
                
                DailySchedule overrideDailySchedule = new DailySchedule(newStart, newStop, newLunch_start, newLunch_stop, newInterval, newGrace_period, newDock, newLunchDeduct);

                
            }
            
            
            
            // QUERY 5
            
            query = "SELECT * FROM scheduleoverride WHERE start = ? AND badegid = ?";
            pstSelect = conn.prepareStatement(query);
            pstSelect.setTimestamp(1, Jenny);
            pstSelect.setString(2, b.getId());
            pstSelect.execute();
            resultset = pstSelect.getResultSet();
            resultset.first();
            
            
            if( resultset.next() == true) {
                
                overrideFlag = true;
                
                int newDailyScheduleId = resultset.getInt(6);
                
                // QUERY 5.5
                
                query = "SELECT * FROM dailyshedule WHERE id = ?";
                pstSelect = conn.prepareStatement(query);
                pstSelect.setInt(1, newDailyScheduleId);
                pstSelect.execute();
                resultset = pstSelect.getResultSet();
                resultset.first();

                long newStart = resultset.getLong(2);
                long newStop = resultset.getLong(3);
                long newLunch_start = resultset.getLong(4);
                long newLunch_stop = resultset.getLong(5);
                int newInterval = resultset.getInt(6);
                int newGrace_period = resultset.getInt(7);
                int newDock = resultset.getInt(8);
                int newLunchDeduct = resultset.getInt(9);
  
                DailySchedule overrideDailySchedule = new DailySchedule(newStart, newStop, newLunch_start, newLunch_stop, newInterval, newGrace_period, newDock, newLunchDeduct);

            }
            
            if (overrideFlag == true) {
                
                Shift newShift = new Shift(employeeShiftId, description, overrideDailySchedule);
                
            }
            
            
            
            
            
            
            
        }
        catch(Exception e) { 
            System.err.println("GetShift" + e.toString());
        }
        
        
        
        
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
        
        GregorianCalendar g = TASLogic.makeCal(ts);
        
        //Find day of week
        
        
        //For each day in period, parse punches
        try{
            for (int i = 1; i < 8; i++){
                String day = new SimpleDateFormat("yyyy-MM-dd").format(g.getTimeInMillis());
                

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
                g.setTimeInMillis(g.getTimeInMillis() + MILLIS_IN_DAY);
                
            }
            return payPeriodPunches;
        }
        catch(Exception e){
            System.err.println("** getPayPeriodPunches " + e.toString());
        }
        return null;
    }
    
    public Absenteeism getAbsenteeism(Badge b, long payPeriod){
        
        GregorianCalendar greg_three = new GregorianCalendar();
        greg_three.setTimeInMillis(payPeriod);
        greg_three.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        greg_three.set(Calendar.HOUR_OF_DAY, 0);
        greg_three.set(Calendar.MINUTE, 0);
        greg_three.set(Calendar.SECOND, 0);
        
        Timestamp newTS = new Timestamp(greg_three.getTimeInMillis());
        
        try{
            query = "SELECT * FROM absenteeism WHERE badgeid = ? AND payperiod = ?";
            pstSelect = conn.prepareStatement(query);
            pstSelect.setString(1, b.getId());
            pstSelect.setTimestamp(2, newTS);
            pstSelect.execute();
            resultset = pstSelect.getResultSet();
            resultset.first();
            
            
            String badgeID = resultset.getString(1);
            Timestamp dbts = resultset.getTimestamp(2);
            Double percentage = resultset.getDouble(3);

            Absenteeism a = new Absenteeism(badgeID, dbts.getTime(), percentage);

            return a;
            
            
            
        }
        catch(Exception e){
            System.err.println("** getAbsenteesim " + e.toString());
        }
        return null;
    }
    
    public void insertAbsenteeism(Absenteeism a){
      
        GregorianCalendar greg_three = new GregorianCalendar();
        greg_three.setTimeInMillis(a.getTimeStamp());
        greg_three.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        greg_three.set(Calendar.HOUR_OF_DAY, 0);
        greg_three.set(Calendar.MINUTE, 0);
        greg_three.set(Calendar.SECOND, 0);
        
        Timestamp newTS = new Timestamp(greg_three.getTimeInMillis());
        try{
            
            query = "DELETE FROM absenteeism WHERE badgeid = ? AND payperiod = ?";
            pstSelect = conn.prepareStatement(query);
            pstSelect.setString(1, a.getID());
            pstSelect.setTimestamp(2, newTS);
            pstSelect.execute();
            resultset = pstSelect.getResultSet();
            
            query = "INSERT INTO absenteeism (badgeid,payperiod,percentage) values (?,?,?)";
            pstUpdate = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstUpdate.setString(1, a.getID());
            pstUpdate.setTimestamp(2, newTS);
            pstUpdate.setDouble(3, a.getPercentage());
            pstUpdate.executeUpdate();
            System.out.println("INSERT");
            
        }
        
        catch(Exception e){
            System.err.println("**insertAbsenteeism " + e.toString());
        }   
    }
}
