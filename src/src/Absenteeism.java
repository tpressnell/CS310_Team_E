
package src;


public class Absenteeism {
    
    private String ID;
    private long timeStamp;
    private double percentage;
    
    public Absenteeism(String ID, long TS, double percent){
        this.ID = ID;
        this.timeStamp = TS;
        this.percentage =  percent;
        
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
    
    public String toString(){
        
        StringBuilder output = new StringBuilder();
        
        output.append("#" + ID);
        output.append(" (Pay Period Starting " + timeStamp + "): ");
        output.append(percentage + "%");
        
        return output.toString();
    }
    
}
