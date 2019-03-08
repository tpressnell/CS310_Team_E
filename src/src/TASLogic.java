
package src;

import java.util.ArrayList;

public class TASLogic {
    
    public static int calculateTotalMinutes(ArrayList<Punch> dailyPunchList, Shift shift){
        
        int minutes  = -1;
        final int LUNCH_DEDUCT = 360;
        final int CONVERT_TO_MINUTES = 60000;
        final int LUNCH_BREAK = 30;
        
        if (dailyPunchList.size() == 0){
            minutes = 0;
        }
        
        else if(dailyPunchList.size() == 1){
            minutes = 0;
        }
        
        else if(dailyPunchList.size() == 2){
            long time_in = dailyPunchList.get(0).getOTS();
            long time_out = dailyPunchList.get(1).getOTS();
            
            if((time_out - time_in) > LUNCH_DEDUCT){
                minutes = (int)(((time_out - time_in) / CONVERT_TO_MINUTES) - LUNCH_BREAK);
            }
            
            else{
                minutes = (int)((time_out - time_in) / CONVERT_TO_MINUTES);
            }
            
            
        }
        
        else if (dailyPunchList.size() == 3){
            minutes = 0;
        }
        
        else if (dailyPunchList.size() == 4){
            long time_in = dailyPunchList.get(0).getOTS();
            long lunch_start = dailyPunchList.get(1).getOTS();
            long lunch_stop = dailyPunchList.get(2).getOTS();
            long time_out = dailyPunchList.get(3).getOTS();
            
            long lunch_break = lunch_stop - lunch_start;
            
            if((time_out - time_in) > LUNCH_DEDUCT){
                minutes = (int)(((time_out - time_in) - lunch_break) / CONVERT_TO_MINUTES);
            }
            
            else{
                 minutes = (int)((time_out - time_in) / CONVERT_TO_MINUTES);  
            }
        }
        
        else {
            minutes = -1;
        }
        
        return minutes;
    }
    
    public static String getPunchListAsJSON(ArrayList<Punch> dailyPunchList){
        
        return null;
    }
}
