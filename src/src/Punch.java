package src;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import src.Badge;
import java.util.Date;


public class Punch{
    
   
    final int CLOCK_OUT = 0;
    final int CLOCK_IN = 1;
    final int TIME_OUT = 2;
    private int year, month, day, hour, minute, second, type;
    long mSecond;
    private Badge id;
    private String name, idNum, otStamp;
    public GregorianCalendar greg;
    
    
    
    public Punch(Badge inBadge, long ms, int type, String otStamp){
        
        this.type = type;
        this.mSecond = ms;
        this.name = inBadge.getName();
        this.idNum = inBadge.getId();
        this.otStamp = otStamp;
        
        // Create Gregorian Calendar Object and name him Greg
        greg = new GregorianCalendar();
        greg.setTimeInMillis(ms);
        
    }
    
  
// Getters 
    
    public String getIdNum() {
        return idNum;
    }
    
    public int getYear() {
        return greg.YEAR;
    }

    public int getMonth() {
        return greg.MONTH;
    }

    public int getDay() {
        return greg.DAY_OF_MONTH;
    }
    public int getDayOfWeek() {
        return greg.get(Calendar.DAY_OF_WEEK);
    }

    public int getHour() {
        return greg.HOUR_OF_DAY;
    }

    public int getMinute() {
        return greg.MINUTE;
    }

    public int getSecond() {
        return greg.SECOND;
    }

    public int getmSecond() {
        return greg.MILLISECOND;
    }

    public Badge getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public GregorianCalendar getGreg() {
        return greg;
    }
    
    
    
    // Setters

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public void setYear(int year) {
        greg.set(Calendar.YEAR, year);
    }

    public void setMonth(int month) {
        greg.set(Calendar.MONTH, month);
    }

    public void setDay(int day) {
        greg.set(Calendar.DAY_OF_YEAR, day);
    }
    public void setDayOfWeek(int dayOfWeek){
        greg.set(Calendar.DAY_OF_WEEK, dayOfWeek);
    }

    public void setHour(int hour) {
        greg.set(Calendar.HOUR_OF_DAY, hour);
    }

    public void setMinute(int minute) {
        greg.set(Calendar.MINUTE, minute);
    }

    public void setSecond(int second) {
        greg.set(Calendar.SECOND, second);
    }

    public void setmSecond(int mSecond) {
        greg.set(Calendar.MILLISECOND, mSecond);
    }

    public void setId(Badge id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setGreg(GregorianCalendar greg) {
        this.greg = greg;
    }
    
    public String printOriginalTimestamp(){
        
        StringBuilder output = new StringBuilder("");
        
        output.append( "#"+ this.getIdNum());
        
        if(this.getType() == 0)
            output.append(" CLOCKED OUT: ");
        else if(this.getType() == 1)
            output.append(" CLOCKED IN: ");
        else if(this.getType() == 2)
            output.append(" TIMED OUT: ");
        
        switch(this.getDayOfWeek()){
            case 1:
                output.append("SUN ");
                break;
            case 2:
                output.append("MON ");
                break;
            case 3:
                output.append("TUE ");
                break;
            case 4:
                output.append("WED ");
                break;
            case 5:
                output.append("THU ");
                break;
            case 6:
                output.append("FRI ");
                break;
            case 7:
                output.append("SAT ");
                break;
                
        }
        
        String[] timestamp = otStamp.split(" ");
        
        String date = timestamp[0];
        String[] date_pieces = date.split("-");
        
        String time = timestamp[1];
        String[] time_pieces = time.split(":");
        
        output.append(date_pieces[1] + "/" + date_pieces[2] + "/" + date_pieces[0]+ " ");
        output.append(time_pieces[0] + ":" + time_pieces[1] + ":" + time_pieces[2].substring(0, 2));
        
        System.out.println(output.toString());
        
        
        return output.toString();
    }
 
}
