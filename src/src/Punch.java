package src;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import src.Badge;
import java.util.Date;


public class Punch{
    
   
    public static final int CLOCK_OUT = 0;
    public static final int CLOCK_IN = 1;
    public static final int TIME_OUT = 2;
    private int punchType;
    private long originalTimestamp;
    private Badge Badge;
    private String otStamp;
    public GregorianCalendar greg;
    private int punchID;
    private int termId; 
    
    
    
    public Punch(Badge inBadge, long ms, int typeID, String otStamp, int punchId, int tID){
        
        this.punchType = typeID;
        this.mSecond = ms;
        this.otStamp = otStamp;
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
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        long longTS = ts.getTime();
        this.longTimestamp= longTS;
    }
    
  
// Getters
    

    public int getTerminalid() {
        return termId;
    }
    
    public long getLongTS(){
        return this.longTimestamp;
    }
    
    public int getPunchID() {
        return punchID;
    }

    public int getPunchType() {
        return this.punchType;
    }

    public String getotStamp() {
        return this.otStamp;
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
    
    
    
    // Setters
    

    public void setTermID(int termId) {
        this.termId = termId;
    }

    public void setPunchID(int punchID) {
        this.punchID = punchID;
    }

    public void setOtStamp(String otStamp) {
        this.otStamp = otStamp;
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
        
        String[] timestamp = otStamp.split(" ");
        
        String date = timestamp[0];
        String[] date_pieces = date.split("-");
        
        String time = timestamp[1];
        String[] time_pieces = time.split(":");
        
        output.append(date_pieces[1] + "/" + date_pieces[2] + "/" + date_pieces[0]+ " ");
        output.append(time_pieces[0] + ":" + time_pieces[1] + ":" + time_pieces[2].substring(0, 2));
        
        //System.out.println(output.toString());
        
        
        return output.toString();
    }
 
}
