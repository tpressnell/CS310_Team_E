package src;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class Shift {
    
    private DailySchedule defaultSchedule;
    private String description;
    private int numOfDaysInShift;
    private int shiftID;
    
      
    public Shift(int shiftID, String description, DailySchedule defaultSchedule){
        
        this.shiftID = shiftID;
        this.description = description;
        this.defaultSchedule = defaultSchedule;    
        this.numOfDaysInShift = 5;
    }
    
    public int getShiftLength() {
        long shiftLengthInMillis = 0;
        int shiftLengthInMinutes = 0;
        final int CONVERSION_TO_MINUTES = 60000;
        
        shiftLengthInMillis = (this.defaultSchedule.getStop() - this.defaultSchedule.getStart());
        shiftLengthInMinutes = (int) shiftLengthInMillis / CONVERSION_TO_MINUTES;
        
       return shiftLengthInMinutes; 
    }
    
    public int getLunchLength(){
        long lunchLengthInMillis = 0;
        int lunchLengthInMinutes = 0;
        final int CONVERSION_TO_MINUTES = 60000;
        
        lunchLengthInMillis = (this.defaultSchedule.getLunch_stop() - this.defaultSchedule.getLunch_start());
        lunchLengthInMinutes = (int) lunchLengthInMillis / CONVERSION_TO_MINUTES;
        
       return lunchLengthInMinutes;
        
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
        return defaultSchedule.getLunch_stop();
    }
    
    public void setLunch_Stop(long newLunch_stop){
        this.defaultSchedule.setLunch_stop(newLunch_stop);
    }
    
    public int getNumOfDaysInShift(){
        return this.numOfDaysInShift;
    }
    public String getDescription(){
        return this.description;
    }
    
    
    @Override
    public String toString(){
        
        StringBuilder output = new StringBuilder();
        
        String start = new SimpleDateFormat("HH:mm").format(defaultSchedule.getStart());
        String stop = new SimpleDateFormat("HH:mm").format(defaultSchedule.getStop());
        String lunch_start = new SimpleDateFormat("HH:mm").format(defaultSchedule.getLunch_start());
        String lunch_stop = new SimpleDateFormat("HH:mm").format(defaultSchedule.getLunch_stop());
        
        output.append(this.description + ": ");
        output.append(start + " - ");
        output.append(stop + " (");
        output.append(this.getShiftLength() + " minutes); Lunch: ");
        output.append(lunch_start + " - ");
        output.append(lunch_stop + " (");
        output.append(this.getLunchLength() + " minutes)");        
        
        return output.toString();
    }
    
    
    
}
