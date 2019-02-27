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
        return greg.DAY_OF_WEEK;
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
    public void sedDayOfWeek(int dayOfWeek){
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
        
        StringBuilder output = new StringBuilder("");
        
        output.append(this.getIdNum());
        
        if(this.getType() == 0)
            output.append(" CLOCKED OUT: ");
        else if(this.getType() == 2)
            output.append(" CLOCKED IN: ");
        else if(this.getType() == 3)
            output.append(" TIMED OUT: ");
        
        switch(this.getDayOfWeek()){
            case 0:
                output.append("SUN ");
                break;
            case 1:
                output.append("MON ");
                break;
            case 2:
                output.append("TUE ");
                break;
            case 3:
                output.append("WED ");
                break;
            case 4:
                output.append("THU ");
                break;
            case 5:
                output.append("FRI ");
                break;
            case 6:
                output.append("SAT ");
                break;
                
        }
        
        output.append(this.getMonth() + "/" + this.getDay() + "/" + this.getYear() + " ");
        output.append(this.getHour() + ":" + this.getMinute() + ":" + this.getSecond());
        
        System.out.println(output.toString());
        
        
        return output.toString();
    }
 
}
