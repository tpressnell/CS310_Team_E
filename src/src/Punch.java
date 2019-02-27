package src;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import src.Badge;
import java.util.Date;


public class Punch{
    
    private enum PunchTypes{
        CLOCK_IN, CLOCK_OUT, TIME_OUT
    }
    private PunchTypes type;
    private int year, month, day, hour, minute, second;
    long mSecond;
    private Badge id;
    private String name, idNum;
    public GregorianCalendar greg;
    
    
    
    public Punch(Badge inBadge, long ms, PunchTypes type){
        
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

    public PunchTypes getType() {
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

    public void setType(PunchTypes type) {
        this.type = type;
    }

    public void setGreg(GregorianCalendar greg) {
        this.greg = greg;
    }
    
    
    
}
