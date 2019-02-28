package src;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import src.Badge;
import java.util.Date;


public class Punch{
    
   
    final int CLOCK_OUT = 0;
    final int CLOCK_IN = 1;
    final int TIME_OUT = 2;
    private int year, month, day, hour, minute, second, type;
    long mSecond;
    private Badge id;
    private String name, idNum;
    public GregorianCalendar greg;
    
    
    
    public Punch(Badge inBadge, long ms, int type){
        
        this.type = type;
        this.mSecond = ms;
        this.name = inBadge.getName();
        this.idNum = inBadge.getId();
        
        // Create Gregorian Calendar Object and name him Greg
        GregorianCalendar greg = new GregorianCalendar();
        greg.setTimeInMillis(ms);
        
    }
    
  
// Getters 
    
    public String getIdNum() {
        return idNum;
    }
    
    public String getDayOfWeek(){
        String day = "";
        int numDay = greg.DAY_OF_WEEK;
        switch(numDay){
            case 1: day = "SUN";
                break;
            case 2: day = "MON";
                break;
            case 3: day = "TUE";
                break;
            case 4: day = "WED";
                break;
            case 5: day = "THU";
                break;
            case 6: day = "FRI";
                    break;
            case 7: day = "SAT";
                break;
            default:
                break;
        }
        return day;
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

    public Badge getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public GregorianCalendar getGreg() {
        return greg;
    }
    
    
    
    // Setters

    public void setIdNum(String idNum) {
        this.idNum = idNum;
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

    public void setId(Badge id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setGreg(GregorianCalendar greg) {
        this.greg = greg;
    }
    
    public String printOriginalTimestamp(){
        
        String badgeID = this.getIdNum();
        String type = "";
        if(this.getType() == CLOCK_OUT){
            type = "CLOCKED OUT:"; 
        }
        else if(this.getType() == CLOCK_IN){
            type = "CLOCKED IN:";
        }
        else{
            type = "TIMED OUT:";
        }
        String dayOfWeek = this.getDayOfWeek();
        int month = this.getMonth();
        int numDay = this.getDay();
        int year = this.getYear();
        int hour = this.getHour();
        int min = this.getMinute();
        int sec = this.getSecond();
        
        StringBuilder string = new StringBuilder();
        string.append("#");
        string.append(badgeID);
        string.append(" ");
        string.append(type);
        string.append(" ");
        string.append(dayOfWeek);
        string.append(" ");
        string.append(month + "/");
        string.append(numDay + "/");
        string.append(year);
        string.append(" ");
        string.append(hour + ":");
        string.append(min + ":");
        string.append(sec);
        
        
        
        
        
        return string.toString();
    }
    
    
    
}
