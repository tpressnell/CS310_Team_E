package src;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import src.Badge;
import java.util.Date;


public class Punch{
    
    public static final long INTERVAL = 15000;
    public static final long GRACE_PERIOD = 5000;
    public static final int CLOCK_OUT = 0;
    public static final int CLOCK_IN = 1;
    public static final int TIME_OUT = 2;
    private int punchType;
    private Date originalTimestamp;
    private Badge Badge;
    private String stringTimestamp, adjustData;
    public GregorianCalendar greg;
    private int punchID;
    private int termId; 
    private long ots;
    private long ats;
    
    
    
    public Punch(Badge inBadge, long ms, int typeID, String stringTimestamp, int punchId, int tID){
        
        this.punchType = typeID;
        this.ots= ms;
        this.stringTimestamp = stringTimestamp;
        this.punchID = punchId;
        this.termId = tID;
        this.Badge = inBadge;
        
        // Create Gregorian Calendar Object and name him Greg
        greg = new GregorianCalendar();
        greg.setTimeInMillis(ms);
        
    }
    
    public Punch(Badge b, int newTermID, int newPunchID){
        this.Badge = b;
        this.termId = newTermID;
        this.punchID = newPunchID;
        Date date = new Date();
        this.originalTimestamp = date;
        this.ots = date.getTime();
        this.stringTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.ots);
        greg = new GregorianCalendar();
        greg.setTimeInMillis(this.ots);
        
    }
    
  

    public String printOriginalTimestamp(){
        
        StringBuilder output = new StringBuilder("");
        
        output.append( "#"+ this.getBadgeID());
        
        if(this.getPunchType() == 0)
            output.append(" CLOCKED OUT: ");
        else if(this.getPunchType() == 1)
            output.append(" CLOCKED IN: ");
        else if(this.getPunchType() == 2)
            output.append(" TIMED OUT: ");
        
        switch(this.getDayOfWeek()){
            case 1:
                output.append("SUN ");
                break;
            case 2:
                output.append("MON ");
                break;
            case 3:
                output.append("TUE ");
                break;
            case 4:
                output.append("WED ");
                break;
            case 5:
                output.append("THU ");
                break;
            case 6:
                output.append("FRI ");
                break;
            case 7:
                output.append("SAT ");
                break;
                
        }
        
        String OTStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(ots);
        
        output.append(OTStamp);
        
        return output.toString();
    }
    
    public String printAdjustedTimestamp(){
        StringBuilder output = new StringBuilder("");
        
        output.append( "#"+ this.getBadgeID());
        
        if(this.getPunchType() == 0)
            output.append(" CLOCKED OUT: ");
        else if(this.getPunchType() == 1)
            output.append(" CLOCKED IN: ");
        else if(this.getPunchType() == 2)
            output.append(" TIMED OUT: ");
        
        switch(this.getDayOfWeek()){
            case 1:
                output.append("SUN ");
                break;
            case 2:
                output.append("MON ");
                break;
            case 3:
                output.append("TUE ");
                break;
            case 4:
                output.append("WED ");
                break;
            case 5:
                output.append("THU ");
                break;
            case 6:
                output.append("FRI ");
                break;
            case 7:
                output.append("SAT ");
                break;
                
        }
        
        String OTStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(ots);
        
        output.append(OTStamp + " (");
        
        output.append(this.getAdjustData() + ")");
        
        return output.toString();
    }
    
    public void adjust(Shift s){
        
        long shiftStart = s.getStart_Time();
        long shiftEnd = s.getEnd_Time();
        
        long lunchStart = s.getLunch_Start();
        long lunchEnd = s.getLunch_End();
        
        int numOfIntervals;
                
        long timeDifference;
        
        // PUNCH IN
        if(this.punchType == 1){
            
            if( (ots < shiftStart) && ( ots > (shiftStart - INTERVAL) )){ //CLOCK IN LESS THAN 15 MINS EARLY
                
                timeDifference = shiftStart - ots;
                ats = ots + timeDifference;
                adjustData = "Shift Start";
                
                
            }
            
            else if( ots < lunchEnd && ots > lunchStart) { //CLOCK IN EARLY FROM LUNCH
                
                ats = ots + (lunchEnd - ots);
                adjustData = "Lunch End";
                 
            } 
            
            else if(ots > shiftStart && ots < shiftStart + GRACE_PERIOD){ //CLOCK IN LESS THAN 5 MINS LATE
                ats = ots - (ots - shiftStart);
                adjustData = "Shift Start";
            }
            
            else if(ots > shiftStart + GRACE_PERIOD && ots < shiftStart + INTERVAL){  //CLOCK IN THAT LATE ENOUGH FOR A DOCK  
                ats = shiftStart + INTERVAL;
                adjustData = "Shift Start";
            }
            
            else if(ots > lunchEnd && ots < lunchEnd + GRACE_PERIOD){  //CLOCK IN WITHIN GRACE PERIOD FOR LUNCH
                ats = ots - (ots - shiftStart);
                adjustData = "Lunch End";
            }
            
            else if(ots > lunchEnd + GRACE_PERIOD && ots < lunchEnd + INTERVAL){ //CLOCK IN FROM LUNCH THAT IS DOCKED
                ats = lunchEnd + INTERVAL;
                adjustData = "Lunch End";
            }
            
            else{
                
                GregorianCalendar nonConformingClockIn = new GregorianCalendar();
                nonConformingClockIn.setTimeInMillis(ots);
                numOfIntervals = (nonConformingClockIn.get(Calendar.MINUTE)) % 15;
                
                if ( numOfIntervals == 0) {
                    
                    if ((nonConformingClockIn.get(Calendar.MINUTE) / 15) < 0.5) {
                        
                        nonConformingClockIn.set(Calendar.MINUTE, 0);
                        nonConformingClockIn.set(Calendar.SECOND, 0);  
                    }
                    else {
                        nonConformingClockIn.set(Calendar.MINUTE, 15);
                        nonConformingClockIn.set(Calendar.SECOND, 0);
                        
                    }
                }
                else if ( numOfIntervals == 1) {
                    
                    if (((nonConformingClockIn.get(Calendar.MINUTE) - 15) / 15) < 0.5) {
                        
                        nonConformingClockIn.set(Calendar.MINUTE, 15);
                        nonConformingClockIn.set(Calendar.SECOND, 0);  
                    }
                    else {
                        nonConformingClockIn.set(Calendar.MINUTE, 30);
                        nonConformingClockIn.set(Calendar.SECOND, 0);
                        
                    }
                }
                else if ( numOfIntervals == 2) {
                    
                    if (((nonConformingClockIn.get(Calendar.MINUTE) - 30 ) / 15) < 0.5) {
                        
                        nonConformingClockIn.set(Calendar.MINUTE, 30);
                        nonConformingClockIn.set(Calendar.SECOND, 0);  
                    }
                    else {
                        nonConformingClockIn.set(Calendar.MINUTE, 45);
                        nonConformingClockIn.set(Calendar.SECOND, 0);
                        
                    } 
                }
                else if ( numOfIntervals == 3) {
                    
                    if (((nonConformingClockIn.get(Calendar.MINUTE) - 45) / 15) < 0.5) {
                        
                        nonConformingClockIn.set(Calendar.MINUTE, 45);
                        nonConformingClockIn.set(Calendar.SECOND, 0);  
                    }
                    else {
                        
                        nonConformingClockIn.set(Calendar.HOUR, Calendar.HOUR + 1);
                        nonConformingClockIn.set(Calendar.MINUTE, 0);
                        nonConformingClockIn.set(Calendar.SECOND, 0);
                        
                    }
                }
            }
        }
        
        //PUNCH OUT
        if ( this.punchType == 0) {
            
            if ( ots  > shiftEnd && ots < shiftEnd + INTERVAL) { // ClOCK OUT LATE LESS THAN 15 MINS
                
                timeDifference = ots - shiftEnd;
                ats = ots - timeDifference; 
                adjustData = "Shift End";
            }
            
            else if( ots > lunchStart && ots < lunchEnd) { //CLOCK OUT LATE FOR LUNCH
                
                ats = ots - (ots - lunchStart);
                adjustData = "Lunch Start";
            }
            
            else if(ots < shiftEnd && ots > shiftEnd - GRACE_PERIOD){ //CLOCK OUT EARLY LESS THAN 5 MINS
                ats = ots + (shiftEnd - ots);
                adjustData = "Shift End";
            }
            
            else if(ots < shiftEnd - GRACE_PERIOD && ots > shiftEnd - INTERVAL){ //CLOCK EARLY THAT IS DOCKED
                ats = shiftEnd - INTERVAL;
                adjustData = "Shift End";
            }
            
            else if(ots < lunchStart - GRACE_PERIOD && ots > lunchStart - INTERVAL){
                ats = lunchStart - INTERVAL;
                adjustData = "Lunch Start";
            }
 
        }
        
        else if(this.punchType == 2){
            ats = ots - (ots - shiftEnd);
            adjustData = "Shift End";
        }
        
        else{
            GregorianCalendar yeah = new GregorianCalendar();
            yeah.setTimeInMillis(ots);
        }
    
    }

        // Getters
    

    public int getTerminalid() {
        return termId;
    }
    
    public Date getOriginalTimestamp(){
        return this.originalTimestamp;
    }
    
    public long getOTS(){
        return this.ots;
    }
    
    public long getATS(){
        return this.ats;
    }
    
    public int getPunchID() {
        return punchID;
    }

    public int getPunchType() {
        return this.punchType;
    }

    public String getStringTimestamp() {
        return this.stringTimestamp;
    }
      
    public String getBadgeID() {
        return Badge.getId();
    }
    
    public int getYear() {
        return greg.YEAR;
    }

    public int getMonth() {
        return greg.MONTH;
    }

    public int getDay() {
        return greg.DAY_OF_MONTH;
    }
    public int getDayOfWeek() {
        return greg.get(Calendar.DAY_OF_WEEK);
    }

    public int getHour() {
        return greg.HOUR_OF_DAY;
    }

    public int getMinute() {
        return greg.MINUTE;
    }

    public int getSecond() {
        return greg.SECOND;
    }

    public int getmSecond() {
        return greg.MILLISECOND;
    }

    public String getName() {
        return Badge.getName();
    }

    public GregorianCalendar getGreg() {
        return greg;
    }
    public String getAdjustData(){
        return adjustData;
    }
    
    
    
    // Setters
    

    public void setTermID(int termId) {
        this.termId = termId;
    }

    public void setPunchID(int punchID) {
        this.punchID = punchID;
    }

    public void setStringTimestamp(String stringTimestamp) {
        this.stringTimestamp = stringTimestamp;
    }

    public void setYear(int year) {
        greg.set(Calendar.YEAR, year);
    }

    public void setMonth(int month) {
        greg.set(Calendar.MONTH, month);
    }

    public void setDay(int day) {
        greg.set(Calendar.DAY_OF_YEAR, day);
    }
    public void setDayOfWeek(int dayOfWeek){
        greg.set(Calendar.DAY_OF_WEEK, dayOfWeek);
    }

    public void setHour(int hour) {
        greg.set(Calendar.HOUR_OF_DAY, hour);
    }

    public void setMinute(int minute) {
        greg.set(Calendar.MINUTE, minute);
    }

    public void setSecond(int second) {
        greg.set(Calendar.SECOND, second);
    }

    public void setmSecond(int mSecond) {
        greg.set(Calendar.MILLISECOND, mSecond);
    }

    public void setPunchType(int type) {
        this.punchType = type;
    }

    public void setGreg(GregorianCalendar greg) {
        this.greg = greg;
    }
    
        
}
 

