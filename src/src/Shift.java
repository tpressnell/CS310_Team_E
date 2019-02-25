package src;

public class Shift {
    
    private long start_time; //Start Time in ms
    private long end_time;  // End Time in ms
    
    public Shift(int st, int et){
        this.start_time = st;
        this.end_time = et;
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
    
    
    
}
