
package src;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONValue;

public class TASLogic {
    
    public static int calculateTotalMinutes(ArrayList<Punch> dailyPunchList, Shift shift){
        
        int minutes  = -1;
        final int LUNCH_DEDUCT = 360;
        final int CONVERT_TO_MINUTES = 60000;
        final int LUNCH_BREAK = 30;
        
        if (dailyPunchList.isEmpty()){
            minutes = 0;
        }
        
        else if(dailyPunchList.size() == 1){
            minutes = 0;
        }
        
        else if(dailyPunchList.size() == 2){
            long time_in = dailyPunchList.get(0).getATS();
            long time_out = dailyPunchList.get(1).getATS();
            
            if(((time_out - time_in) / CONVERT_TO_MINUTES) > LUNCH_DEDUCT){
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
            long time_in = dailyPunchList.get(0).getATS();
            long lunch_start = dailyPunchList.get(1).getATS();
            long lunch_stop = dailyPunchList.get(2).getATS();
            long time_out = dailyPunchList.get(3).getATS();
            
            long lunch_break = lunch_stop - lunch_start;
            
            if(((time_out - time_in) / CONVERT_TO_MINUTES) > LUNCH_DEDUCT){
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
        ArrayList<HashMap<String, String>> jsonData = new ArrayList();
        String results;
        
        for(Punch p : dailyPunchList){
            
            HashMap<String, String> punchData = new HashMap();
            
            punchData.put("id", String.valueOf(p.getPunchID()));
            punchData.put("badgeid", p.getBadgeID());
            punchData.put("terminalid", String.valueOf(p.getTerminalid()));
            punchData.put("punchtypeid", String.valueOf(p.getPunchType()));
            punchData.put("punchdata", p.getAdjustData());
            punchData.put("originaltimestamp", String.valueOf(p.getOTS()));
            punchData.put("adjustedtimestamp", String.valueOf(p.getATS()));
            
            jsonData.add(punchData);
        }
        
        results = JSONValue.toJSONString(jsonData);
        
        return results;
        
    }
    public static double calculateAbsenteeism(ArrayList<Punch> punchlist, Shift shift) {
       int dailyPunches = 4;
       int totalAccuredTime = 0;
       int totalShiftTime = 0;
       
        
       for(int i = 0; i < punchlist.size(); i = i + 4){
           ArrayList<Punch> dailyPunchList = new ArrayList<>();
           
           for(int j = 0; j < dailyPunches; j++){
               dailyPunchList.add(punchlist.get(i));       
           }
           totalAccuredTime += TASLogic.calculateTotalMinutes(dailyPunchList, shift);
           
        }
       
       
       
       
       
       double percent = 0.00; 
       
        
        
        return percent;
    }
}
