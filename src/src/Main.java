
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
        System.out.println(s.getShiftLength());
        
        long ts = p.getOTS();
        ArrayList<Punch> punchlist = db.getPayPeriodPunchList(b, ts);
        
        for(int i = 0 ; i < punchlist.size(); i++){
            punchlist.get(i).adjust(s);
            System.out.println("Day of Week: " + punchlist.get(i).getDayOfWeek());
            System.out.println( punchlist.get(i).getATS() / 60000);
            System.out.println("\n");
        }
        
        
        
        
        
        
    }
    
}
