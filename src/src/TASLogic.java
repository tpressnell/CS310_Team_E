
package src;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
    
    public static int calculateAccuredMinutes(ArrayList<Punch> dailyPunchList, Shift shift){
        
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
            long time_in = dailyPunchList.get(0).getOTS();
            long time_out = dailyPunchList.get(1).getOTS();
            
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
            long time_in = dailyPunchList.get(0).getOTS();
            long lunch_start = dailyPunchList.get(1).getOTS();
            long lunch_stop = dailyPunchList.get(2).getOTS();
            long time_out = dailyPunchList.get(3).getOTS();
            
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
       final int LUNCH_BREAK = 360;
       final int CONVERT_TO_MINS = 60000; 
       final int DAILY_PUNCHES = 4;
       double totalAccuredTime = 0;
       double totalShiftTime = 0;
       double percent = 0.0;
       
       System.out.println("ArrayList size: " + punchlist.size());
       
       ArrayList<ArrayList<Punch>> dailyPunchLists = new ArrayList<>(); //ArrayList to contain parsed DailyPunchLists
       
       GregorianCalendar startOfPP = new GregorianCalendar();
       startOfPP.setTimeInMillis(punchlist.get(0).getOTS());
       
       startOfPP.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
       startOfPP.set(Calendar.HOUR_OF_DAY, 0);
       startOfPP.set(Calendar.MINUTE, 0);
       startOfPP.set(Calendar.SECOND, 0);
       
       
       int currentDayOfWeek = 1; //Set Day_of_Week counter to SUNDAY
      
       for(int i = 1; i < 8; i++){
           ArrayList<Punch> dayOfPunches = new ArrayList<>();
           for(int j = 0 ; j < punchlist.size(); j++){
               if(punchlist.get(j).getDayOfWeek() == i)
                   dayOfPunches.add(punchlist.get(j));
           }
           dailyPunchLists.add(dayOfPunches);
       }
       
       for(int i = 0; i < dailyPunchLists.size(); i++){ //Loop through Complete dailyPunchLists and Calc total AccuredTime and totalShiftTime
          totalAccuredTime += TASLogic.calculateAccuredMinutes(dailyPunchLists.get(i), shift);
          if(dailyPunchLists.get(i).get(0).getDayOfWeek() != 1 ||dailyPunchLists.get(i).get(0).getDayOfWeek() != 7){
              totalShiftTime += 480;
          }
      }
      
      System.out.println(totalAccuredTime);
       System.out.println(totalShiftTime);
             
       percent = (1 - (totalAccuredTime/totalShiftTime)) * 100; //Calc absenteeism as percentage
  
       return percent;
    }
}
