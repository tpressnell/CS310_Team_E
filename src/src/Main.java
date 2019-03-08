
package src;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Main {
   
    public static void main(String[] args){
        
        TASDatabase db = new TASDatabase();
        
        Punch p = db.getPunch(3634);
        Badge b = db.getBadge(p.getBadgeID());
        Shift s = db.getShift(b);
        
        ArrayList<Punch> dailypunchlist = db.getDailyPunchList(b, p.getOTS());
        
        for(Punch p1 : dailypunchlist){
            p1.adjust(s);
            System.out.println(p1.getOTS() + " " + p1.getATS());
        }
        
        
    }
    
}
