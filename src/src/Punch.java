package src;

import java.util.Calendar;
import src.Badge;

public class Punch {
    
    private int idNum, year, month, day, hour, minute, second, mSecond;
    private Badge id;
    private String name;
    
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
}