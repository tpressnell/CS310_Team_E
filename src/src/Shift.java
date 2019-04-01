package src;

import java.util.HashMap;

public class Shift {
    
    private DailySchedule defaultSchedule;
    private int numOfDaysInShift;
    private int shiftID;
    
    public Shift(int shiftID, String description, DailySchedule defaultSchedule){
        
        this.shiftID = shiftID;
        this.defaultSchedule = defaultSchedule;
        
        if(shiftID == 1 || shiftID == 2 || shiftID == 3 || shiftID == 4)
            this.numOfDaysInShift = 5;
    }
    public int getShiftLength() {
        long shiftLengthInMillis = 0;
        int shiftLengthInMinutes = 0;
        final int CONVERSION_TO_MINUTES = 60000;
        
        shiftLengthInMillis = (this.getEnd_Time() - this.getStart_Time()) - (this.lunch_end - this.lunch_start);
        shiftLengthInMinutes = (int) shiftLengthInMillis / CONVERSION_TO_MINUTES;
        
       return shiftLengthInMinutes; 
    }

    public long getStart_Time() {
        return defaultSchedule.getStart();
    }

    public void setStart_Time(long start_time) {
        this.defaultSchedule.setStart(start_time);
    }

    public long getStop_Time() {
        return defaultSchedule.getStop();
    }

    public void setStop_Time(long Stop_time) {
        this.defaultSchedule.setStop(Stop_time);
        
    }
    
    public int getID(){
        return this.shiftID;
    }
    
    public void setID(int newID){
        this.shiftID = newID;
    }
    
    public long getLunch_Start(){
        return defaultSchedule.getLunch_start();
    }
    
    public void setLunch_Start(long newLunch_start){
        this.defaultSchedule.setLunch_start(newLunch_start);
    }
    
    public long getLunch_Stop(){
        return defaultSchedule.getLunch_stop()
    }
    
    public void setLunch_Stop(long newLunch_stop){
        this.defaultSchedule.setLunch_stop(newLunch_stop);
    }
    
    public int getNumOfDaysInShift(){
        return this.numOfDaysInShift;
    }
    @Override
    public String toString(){
        String string;
        if(this.getID() == 1){
           string = "Shift 1: 07:00 - 15:30 (510 minutes); Lunch: 12:00 - 12:30 (30 minutes)";
        }
        else if(this.getID() == 2){
            string = "Shift 2: 12:00 - 20:30 (510 minutes); Lunch: 16:30 - 17:00 (30 minutes)";
        }
        else if(this.getID() == 3){
            string = "Shift 1 Early Lunch: 07:00 - 15:30 (510 minutes); Lunch: 11:30 - 12:00 (30 minutes)";
        }
        else{
            string = "Shift 3: 22:30 - 7:00 (510 minutes); Lunch: 3:30 - 4:00 (30 minutes)";
            
        }
        return string;
    }
    
    
    
}
