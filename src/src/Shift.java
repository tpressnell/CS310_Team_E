package src;

public class Shift {
    
    private long start_time; //Start Time in ms
    private long end_time;  // End Time in ms
    private int shiftID;
    
    public Shift(long st, long et, int id){
        this.start_time = st;
        this.end_time = et;
        this.shiftID = id;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }
    
    public int getID(){
        return this.shiftID;
    }
    
    public void setID(int newID){
        this.shiftID = newID;
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
