
package src;


public class Main {
   
    public static void main(String[] args){
        
        TASDatabase db = new TASDatabase();
        
        Punch p1 = db.getPunch(3433);
        //System.out.println(p1.getDayOfWeek());
        
        System.out.println(p1.printOriginalTimestamp());
    
    }
    
}
