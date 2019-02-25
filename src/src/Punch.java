package src;

import java.util.Calendar;
import java.util.GregorianCalendar;
import src.Badge;

public class Punch {
    
    private int idNum, year, month, day, hour, minute, second, mSecond;
    private Badge id;
    private String name;
    private GregorianCalendar date;
    
    public Punch(Badge inBadge, int yr, int mth, int inDay, int hr, int min, int sec, int ms){
        this.id = inBadge;
        this.year = yr;
        this.month = mth;
        this.day = inDay;
        this.hour = hr;
        this.minute = min;
        this.second = sec;
        this.mSecond = ms;
        
        this.name = inBadge.getName();
        this.idNum = inBadge.getId();
        
    }
    // Make Greg Calendar Object 
    
    public GregorianCalendar makeCalndar(int calYear, int calMonth, int calDay, int calHour, int calMinute, int calSecond) {
        
        GregorianCalendar date = new GregorianCalendar(calYear, calMonth, calDay,calHour, calMinute, calSecond);
        
      return date;  
    }
    
    
}