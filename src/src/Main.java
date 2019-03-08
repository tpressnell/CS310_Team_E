
package src;


public class Main {
   
    public static void main(String[] args){
        
        TASDatabase db = new TASDatabase();
        
        Punch p1 = db.getPunch(3634);
        Shift s1 = db.getShift(1);
        
        p1.adjust(s1);
        System.out.println(p1.getOTS());
        
    }
    
}
