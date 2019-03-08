
package src;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class Main {
   
    public static void main(String[] args){
        
        TASDatabase db = new TASDatabase();
        
        Punch p1 = db.getPunch(1162);
        Shift s1 = db.getShift(1);
        
        p1.adjust(s1);
        System.out.println(p1.printOriginalTimestamp());
       System.out.println(p1.printAdjustedTimestamp());
        
        
    }
    
}
