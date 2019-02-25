package src;

import java.util.Calendar;
import src.Badge;

public class Punch {
    
    private int year, month, day, hour, minute, second, mSecond;
    private Badge id;
    private String name;
    
    public Punch(Badge inBadge, int yr, int mth, int inDay, int hr, int min, int sec, int ms){
        this.id = inBadge;
        this.year = yr;
        this.month = mth;
    }
}