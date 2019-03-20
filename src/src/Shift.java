package src;

public class Shift {
    
    private long start_time; //Start Time in ms
    private long end_time;
    private long lunch_start;
    private long lunch_end;// End Time in ms
    private int numOfDaysInShift;
    private int shiftID;
    
    public Shift(long st, long et, int id, long lunch_start, long lunch_end){
        this.start_time = st;
        this.end_time = et;
        this.shiftID = id;
        this.lunch_start = lunch_start;
        this.lunch_end = lunch_end;
        if(shiftID == 1 || shiftID == 2 || shiftID == 3)
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
        return start_time;
    }

    public void setStart_Time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_Time() {
        return end_time;
    }

    public void setEnd_Time(long end_time) {
        this.end_time = end_time;
    }
    
    public int getID(){
        return this.shiftID;
    }
    
    public void setID(int newID){
        this.shiftID = newID;
    }
    
    public long getLunch_Start(){
        return this.lunch_start;
    }
    
    public void setLunch_Start(long newLunch_start){
        this.lunch_start = newLunch_start;
    }
    
    public long getLunch_End(){
        return this.lunch_end;
    }
    
    public void setLunc_End(long newLunch_end){
        this.lunch_end = newLunch_end;
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
