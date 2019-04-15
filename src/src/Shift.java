package src;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class Shift {
    
    private DailySchedule defaultSchedule;
    private String description;
    private int numOfDaysInShift;
    private int shiftID;
    private HashMap<Integer, DailySchedule> workSchedule;
    
      
    public Shift(int shiftID, String description, DailySchedule defaultSchedule){
        
        this.shiftID = shiftID;
        this.description = description;
        this.defaultSchedule = defaultSchedule;    
        this.numOfDaysInShift = 5;
        this.workSchedule = new HashMap<>();
        for(int i = 1; i < 8; i++){
            //if(i != 1 || i != 7){
                workSchedule.put(i, defaultSchedule);
            //}
        }
    }
    
    public void setOverride(DailySchedule override, int dayOfWeek){
        workSchedule.put(dayOfWeek, override);
    }
    
    public DailySchedule getDailySchdedule(int dayOfWeek){
        return workSchedule.get(dayOfWeek);
    }
    
    public int getShiftStartHour(){
        GregorianCalendar g = new GregorianCalendar();
        g.setTimeInMillis(defaultSchedule.getStart());
        return g.get(Calendar.HOUR_OF_DAY);
    }
    
    public int getShiftStartHour(int day){
        DailySchedule DS = workSchedule.get(day);
        GregorianCalendar g = new GregorianCalendar();
        g.setTimeInMillis(DS.getStart());
        return g.get(Calendar.HOUR_OF_DAY);
    }
    
    public int getShiftLength() {
        long shiftLengthInMillis = 0;
        int shiftLengthInMinutes = 0;
        final int CONVERSION_TO_MINUTES = 60000;
        
        shiftLengthInMillis = (this.defaultSchedule.getStop() - this.defaultSchedule.getStart());
        shiftLengthInMinutes = (int) shiftLengthInMillis / CONVERSION_TO_MINUTES;
        
       return shiftLengthInMinutes; 
    }
    
    public int getShiftLength(int day){
        DailySchedule DS = workSchedule.get(day);
        long shiftLengthInMillis = 0;
        int shiftLengthInMinutes = 0;
        final int CONVERSION_TO_MINUTES = 60000;
        
        shiftLengthInMillis = (DS.getStop() - DS.getStart());
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
    
    public int getLunchLength(int day){
        DailySchedule DS = workSchedule.get(day);
        long lunchLengthInMillis = 0;
        int lunchLengthInMinutes = 0;
        final int CONVERSION_TO_MINUTES = 60000;
        
        lunchLengthInMillis = (DS.getLunch_stop() - DS.getLunch_start());
        lunchLengthInMinutes = (int) lunchLengthInMillis / CONVERSION_TO_MINUTES;
        
        return lunchLengthInMinutes;
    }

    public long getStart_Time() {
        return defaultSchedule.getStart();
    }
    
    public long getStart_Time(int day){
        DailySchedule DS = workSchedule.get(day);
        return DS.getStart();
    }

    public void setStart_Time(long start_time) {
        this.defaultSchedule.setStart(start_time);
    }
    public void setStart_Time(long start_time, int day){
        DailySchedule DS = workSchedule.get(day);
        DS.setStart(start_time);
        workSchedule.put(day, DS);
    }

    public long getStop_Time() {
        return defaultSchedule.getStop();
    }
    
    public long getStop_Time(int day){
        DailySchedule DS = workSchedule.get(day);
        return DS.getStop();    
    }

    public void setStop_Time(long Stop_time) {
        this.defaultSchedule.setStop(Stop_time);
        
    }
    
    public void setStop_Time(long stop_time, int day){
        DailySchedule DS = workSchedule.get(day);
        DS.setStop(stop_time);
        workSchedule.put(day, DS);
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
    
    public long getLunch_Start(int day){
        DailySchedule DS = workSchedule.get(day);
        return DS.getLunch_start();
    }
    
    public void setLunch_Start(long newLunch_start){
        this.defaultSchedule.setLunch_start(newLunch_start);
    }
    
    public void setLunch_Start(long lunch_start, int day){
        DailySchedule DS = workSchedule.get(day);
        DS.setLunch_start(lunch_start);
        workSchedule.put(day, DS);
    }
    
    public long getLunch_Stop(){
        return defaultSchedule.getLunch_stop();
    }
    
    public long getLunch_Stop(int day){
        DailySchedule DS = workSchedule.get(day);
        return DS.getLunch_stop();
    }
    
    public void setLunch_Stop(long newLunch_stop){
        this.defaultSchedule.setLunch_stop(newLunch_stop);
    }
    
    public void setLunch_Stop(long lunch_stop, int day){
        DailySchedule DS = workSchedule.get(day);
        DS.setLunch_stop(lunch_stop);
        workSchedule.put(day, DS);
    }
    
    public int getNumOfDaysInShift(){
        return this.numOfDaysInShift;
    }
    public String getDescription(){
        return this.description;
    }
    
    public void printWorkSchedule(){
        for(int i = 1 ; i < 8; i++){
            System.out.println(workSchedule.get(i).getStart());
        }
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
