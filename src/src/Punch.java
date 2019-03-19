package src;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;


public class Punch{
    
    public static final long INTERVAL = 900000;
    public static final long GRACE_PERIOD = 300000;
    public static final int CLOCK_OUT = 0;
    public static final int CLOCK_IN = 1;
    public static final int TIME_OUT = 2;
    private int punchType;
    private Date originalTimestamp;
    private Badge Badge;
    private String stringTimestamp, adjustData;
    public GregorianCalendar greg;
    private int punchID;
    private int termId; 
    private long ots;
    private long ats;
    
    
    
    public Punch(Badge inBadge, long ms, int typeID, String stringTimestamp, int punchId, int tID){
        
        this.punchType = typeID;
        this.ots= ms;
        this.stringTimestamp = stringTimestamp;
        this.punchID = punchId;
        this.termId = tID;
        this.Badge = inBadge;
        
        // Create Gregorian Calendar Object and name him Greg
        greg = new GregorianCalendar();
        greg.setTimeInMillis(ms);
        
    }
    
    public Punch(Badge b, int newTermID, int newPunchID){
        this.Badge = b;
        this.termId = newTermID;
        this.punchID = newPunchID;
        Date date = new Date();
        this.originalTimestamp = date;
        this.ots = date.getTime();
        this.stringTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.ots);
        greg = new GregorianCalendar();
        greg.setTimeInMillis(this.ots);
        
    }
    
  

    public String printOriginalTimestamp(){
        
        StringBuilder output = new StringBuilder("");
        
        output.append( "#"+ this.getBadgeID());
        
        if(this.getPunchType() == 0)
            output.append(" CLOCKED OUT: ");
        else if(this.getPunchType() == 1)
            output.append(" CLOCKED IN: ");
        else if(this.getPunchType() == 2)
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
        
        String OTStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(ots);
        
        output.append(OTStamp);
        
        return output.toString();
    }
    
    public String printAdjustedTimestamp(){
        StringBuilder output = new StringBuilder("");
        
        output.append( "#"+ this.getBadgeID());
        
        if(this.getPunchType() == 0)
            output.append(" CLOCKED OUT: ");
        else if(this.getPunchType() == 1)
            output.append(" CLOCKED IN: ");
        else if(this.getPunchType() == 2)
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
        
        String OTStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(ats);
        
        output.append(OTStamp + " (");
        
        output.append(this.getAdjustData() + ")");
        
        return output.toString();
    }
    
    public void adjust(Shift s){
        
        GregorianCalendar OTS = new GregorianCalendar();
        OTS.setTimeInMillis(ots);
        
        long shiftStart = s.getStart_Time();
        
        GregorianCalendar SSGC = new GregorianCalendar();
        SSGC.setTimeInMillis(shiftStart);
        SSGC.set(Calendar.YEAR, OTS.get(Calendar.YEAR));
        SSGC.set(Calendar.MONTH, OTS.get(Calendar.MONTH));
        SSGC.set(Calendar.DAY_OF_MONTH, OTS.get(Calendar.DAY_OF_MONTH));
        shiftStart = SSGC.getTimeInMillis();
        
        
        long shiftEnd = s.getEnd_Time();
        
        GregorianCalendar SEGC = new GregorianCalendar();
        SEGC.setTimeInMillis(shiftEnd);
        SEGC.set(Calendar.YEAR, OTS.get(Calendar.YEAR));
        SEGC.set(Calendar.MONTH, OTS.get(Calendar.MONTH));
        SEGC.set(Calendar.DAY_OF_MONTH, OTS.get(Calendar.DAY_OF_MONTH));
        shiftEnd = SEGC.getTimeInMillis();
        
        long lunchStart = s.getLunch_Start();
        
        GregorianCalendar LSGC = new GregorianCalendar();
        LSGC.setTimeInMillis(lunchStart);
        LSGC.set(Calendar.YEAR, OTS.get(Calendar.YEAR));
        LSGC.set(Calendar.MONTH, OTS.get(Calendar.MONTH));
        LSGC.set(Calendar.DAY_OF_MONTH, OTS.get(Calendar.DAY_OF_MONTH));
        lunchStart = LSGC.getTimeInMillis();
        
        long lunchEnd = s.getLunch_End();
        
        GregorianCalendar LEGC = new GregorianCalendar();
        LEGC.setTimeInMillis(lunchEnd);
        LEGC.set(Calendar.YEAR, OTS.get(Calendar.YEAR));
        LEGC.set(Calendar.MONTH, OTS.get(Calendar.MONTH));
        LEGC.set(Calendar.DAY_OF_MONTH, OTS.get(Calendar.DAY_OF_MONTH));
        lunchEnd = LEGC.getTimeInMillis();
          
        int numOfIntervals;    
        long timeDifference;
        boolean isWeekEnd = false;
         
       if(OTS.get(Calendar.DAY_OF_WEEK) == 1 || OTS.get(Calendar.DAY_OF_WEEK) == 7) {
           
           isWeekEnd = true;
           
       }
        
       if(!isWeekEnd){
           
            // PUNCH IN
            if(this.punchType == 1){

                if( (ots < shiftStart) && ( ots > (shiftStart - INTERVAL) )){ //CLOCK IN LESS THAN 15 MINS EARLY

                    ats = ots + (shiftStart - ots);
                    adjustData = "Shift Start";  

                }

                else if( ots < lunchEnd && ots > lunchStart) { //CLOCK IN EARLY FROM LUNCH

                    ats = lunchEnd;
                    adjustData = "Lunch Stop";


                } 

                else if(ots > shiftStart && ots < shiftStart + GRACE_PERIOD){ //CLOCK IN LESS THAN 5 MINS LATE
                    ats = ots - (ots - shiftStart);
                    adjustData = "Shift Start";

                }

                else if(ots > shiftStart + GRACE_PERIOD && ots < shiftStart + INTERVAL){  //CLOCK IN THAT LATE ENOUGH FOR A DOCK  
                    ats = shiftStart + INTERVAL;
                    adjustData = "Shift Dock";

                }

                else if(ots > lunchEnd && ots < lunchEnd + GRACE_PERIOD){  //CLOCK IN WITHIN GRACE PERIOD FOR LUNCH
                    ats = ots - (ots - shiftStart);
                    adjustData = "Lunch Stop";

                }

                else if(ots > lunchEnd + GRACE_PERIOD && ots < lunchEnd + INTERVAL){ //CLOCK IN FROM LUNCH THAT IS DOCKED
                    ats = lunchEnd + INTERVAL;
                    adjustData = "Lunch Stop";
                }

                else if(OTS.get(Calendar.MINUTE) == 0 || OTS.get(Calendar.MINUTE) == 15 || OTS.get(Calendar.MINUTE) == 30 || OTS.get(Calendar.MINUTE) == 45 ){
                    OTS.set(Calendar.SECOND, 0);
                    ats = OTS.getTimeInMillis();
                    adjustData = "None";
                }

                else{
                    GregorianCalendar NCCI = new GregorianCalendar();
                    NCCI.setTimeInMillis(ots);

                    if(NCCI.get(Calendar.MINUTE) > 0 && NCCI.get(Calendar.MINUTE) < 15 ){
                        double minSec = NCCI.get(Calendar.MINUTE) + (NCCI.get(Calendar.SECOND) /60);
                        if(minSec < 7.5){
                            NCCI.set(Calendar.MINUTE, 0);
                            NCCI.set(Calendar.SECOND, 0);
                            ats = NCCI.getTimeInMillis();
                            adjustData = "Interval Round";
                        }
                        else{
                            NCCI.set(Calendar.MINUTE, 15);
                            NCCI.set(Calendar.SECOND, 0);
                            ats = NCCI.getTimeInMillis();
                            adjustData = "Interval Round";
                        }

                    }

                    else if(NCCI.get(Calendar.MINUTE) > 15 && NCCI.get(Calendar.MINUTE) < 30){
                        double minSec = NCCI.get(Calendar.MINUTE) + (NCCI.get(Calendar.SECOND) /60);
                        if(minSec < 22.5){
                            NCCI.set(Calendar.MINUTE, 15);
                            NCCI.set(Calendar.SECOND, 0);
                            ats = NCCI.getTimeInMillis();
                            adjustData = "Interval Round";
                        }
                        else{
                            NCCI.set(Calendar.MINUTE, 30);
                            NCCI.set(Calendar.SECOND, 0);
                            ats = NCCI.getTimeInMillis();
                            adjustData = "Interval Round";
                        }
                    }

                    else if(NCCI.get(Calendar.MINUTE) > 30 && NCCI.get(Calendar.MINUTE) < 45){
                        double minSec = NCCI.get(Calendar.MINUTE) + (NCCI.get(Calendar.SECOND) /60);
                        if(minSec < 37.5){
                            NCCI.set(Calendar.MINUTE, 30);
                            NCCI.set(Calendar.SECOND, 0);
                            ats = NCCI.getTimeInMillis();
                            adjustData = "Interval Round";
                        }
                        else{
                            NCCI.set(Calendar.MINUTE, 45);
                            NCCI.set(Calendar.SECOND, 0);
                            ats = NCCI.getTimeInMillis();
                            adjustData = "Interval Round";
                        }
                    }

                    else if(NCCI.get(Calendar.MINUTE) > 45 && NCCI.get(Calendar.MINUTE) <= 59){
                        double minSec = NCCI.get(Calendar.MINUTE) + (NCCI.get(Calendar.SECOND) /60);
                        if(minSec < 52.5){
                            NCCI.set(Calendar.MINUTE, 45);
                            NCCI.set(Calendar.SECOND, 0);
                            ats = NCCI.getTimeInMillis();
                            adjustData = "Interval Round";
                        }
                        else{
                            NCCI.set(Calendar.HOUR_OF_DAY, (NCCI.get(Calendar.HOUR_OF_DAY) + 1));
                            NCCI.set(Calendar.MINUTE, 0);
                            NCCI.set(Calendar.SECOND, 0);
                            ats = NCCI.getTimeInMillis();
                            adjustData = "Interval Round";
                        }
                    }
                }

            }
        
        //PUNCH OUT
        if ( this.punchType == 0) {
            
            if ( ots  > shiftEnd && ots < shiftEnd + INTERVAL) { // ClOCK OUT LATE LESS THAN 15 MINS
                
                timeDifference = ots - shiftEnd;
                ats = ots - timeDifference; 
                adjustData = "Shift Stop";
            }
            
            else if( ots > lunchStart && ots < lunchEnd) { //CLOCK OUT LATE FOR LUNCH
                
                ats = lunchStart;
                adjustData = "Lunch Start";
            }
            
            else if(ots < shiftEnd && ots > shiftEnd - GRACE_PERIOD){ //CLOCK OUT EARLY LESS THAN 5 MINS
                ats = ots + (shiftEnd - ots);
                adjustData = "Shift Stop";
            }
            
            else if((ots < shiftEnd - GRACE_PERIOD && ots > shiftEnd - INTERVAL) || ots == shiftEnd - INTERVAL){ //CLOCK EARLY THAT IS DOCKED
                ats = shiftEnd - INTERVAL;
                adjustData = "Shift Dock";
            }
            
            else if(ots < lunchStart - GRACE_PERIOD && ots > lunchStart - INTERVAL){
                ats = lunchStart - INTERVAL;
                adjustData = "Lunch Start";
            }
            else if(OTS.get(Calendar.MINUTE) == 0 || OTS.get(Calendar.MINUTE) == 15 || OTS.get(Calendar.MINUTE) == 30 || OTS.get(Calendar.MINUTE) == 45 ){
                OTS.set(Calendar.SECOND, 0);
                ats = OTS.getTimeInMillis();
                adjustData = "None"; 
            } 
           else{
                GregorianCalendar NCCI = new GregorianCalendar();
                NCCI.setTimeInMillis(ots);
                
                if(NCCI.get(Calendar.MINUTE) > 0 && NCCI.get(Calendar.MINUTE) < 15 ){
                    double minSec = 0;
                    if(NCCI.get(Calendar.MINUTE) == 7)
                        if(NCCI.get(Calendar.SECOND) >= 30)
                            minSec = 7.6;
             
                    if(minSec < 7.5){
                        NCCI.set(Calendar.MINUTE, 0);
                        NCCI.set(Calendar.SECOND, 0);
                        ats = NCCI.getTimeInMillis();
                        adjustData = "Interval Round";
                    }
                    else{
                        NCCI.set(Calendar.MINUTE, 15);
                        NCCI.set(Calendar.SECOND, 0);
                        ats = NCCI.getTimeInMillis();
                        adjustData = "Interval Round";
                    }
                        
                }
                
                else if(NCCI.get(Calendar.MINUTE) > 15 && NCCI.get(Calendar.MINUTE) < 30){
                    double minSec = NCCI.get(Calendar.MINUTE) + (NCCI.get(Calendar.SECOND) /60);
                    if(minSec < 22.5){
                        NCCI.set(Calendar.MINUTE, 15);
                        NCCI.set(Calendar.SECOND, 0);
                        ats = NCCI.getTimeInMillis();
                        adjustData = "Interval Round";
                    }
                    else{
                        NCCI.set(Calendar.MINUTE, 30);
                        NCCI.set(Calendar.SECOND, 0);
                        ats = NCCI.getTimeInMillis();
                        adjustData = "Interval Round";
                    }
                }
                
                else if(NCCI.get(Calendar.MINUTE) > 30 && NCCI.get(Calendar.MINUTE) < 45){
                    double minSec = NCCI.get(Calendar.MINUTE) + (NCCI.get(Calendar.SECOND) /60);
                    if(minSec < 37.5){
                        NCCI.set(Calendar.MINUTE, 30);
                        NCCI.set(Calendar.SECOND, 0);
                        ats = NCCI.getTimeInMillis();
                        adjustData = "Interval Round";
                    }
                    else{
                        NCCI.set(Calendar.MINUTE, 45);
                        NCCI.set(Calendar.SECOND, 0);
                        ats = NCCI.getTimeInMillis();
                        adjustData = "Interval Round";
                    }
                }
                
                else if(NCCI.get(Calendar.MINUTE) > 45 && NCCI.get(Calendar.MINUTE) <= 59){
                    double minSec = NCCI.get(Calendar.MINUTE) + (NCCI.get(Calendar.SECOND) /60);
                    if(minSec < 52.5){
                        NCCI.set(Calendar.MINUTE, 45);
                        NCCI.set(Calendar.SECOND, 0);
                        ats = NCCI.getTimeInMillis();
                        adjustData = "Interval Round";
                    }
                    else{
                        NCCI.set(Calendar.HOUR_OF_DAY, (NCCI.get(Calendar.HOUR_OF_DAY) + 1));
                        NCCI.set(Calendar.MINUTE, 0);
                        NCCI.set(Calendar.SECOND, 0);
                        ats = NCCI.getTimeInMillis();
                        adjustData = "Interval Round";
                    }
                }
            }
 
        }
        
        else if(this.punchType == 2){
            ats = ots - (ots - shiftEnd);
            adjustData = "Shift Stop";
        }
        
       }
       else if(isWeekEnd){
                GregorianCalendar NCCI = new GregorianCalendar();
                NCCI.setTimeInMillis(ots);
                
                if(NCCI.get(Calendar.MINUTE) > 0 && NCCI.get(Calendar.MINUTE) < 15 ){
                    double minSec = 0;
                    if(NCCI.get(Calendar.MINUTE) == 7)
                        if(NCCI.get(Calendar.SECOND) >= 30)
                            minSec = 7.6;
             
                    if(minSec < 7.5){
                        NCCI.set(Calendar.MINUTE, 0);
                        NCCI.set(Calendar.SECOND, 0);
                        ats = NCCI.getTimeInMillis();
                        adjustData = "Interval Round";
                    }
                    else{
                        NCCI.set(Calendar.MINUTE, 15);
                        NCCI.set(Calendar.SECOND, 0);
                        ats = NCCI.getTimeInMillis();
                        adjustData = "Interval Round";
                    }
                        
                }
                
                else if(NCCI.get(Calendar.MINUTE) > 15 && NCCI.get(Calendar.MINUTE) < 30){
                    double minSec = NCCI.get(Calendar.MINUTE) + (NCCI.get(Calendar.SECOND) /60);
                    if(minSec < 22.5){
                        NCCI.set(Calendar.MINUTE, 15);
                        NCCI.set(Calendar.SECOND, 0);
                        ats = NCCI.getTimeInMillis();
                        adjustData = "Interval Round";
                    }
                    else{
                        NCCI.set(Calendar.MINUTE, 30);
                        NCCI.set(Calendar.SECOND, 0);
                        ats = NCCI.getTimeInMillis();
                        adjustData = "Interval Round";
                    }
                }
                
                else if(NCCI.get(Calendar.MINUTE) > 30 && NCCI.get(Calendar.MINUTE) < 45){
                    double minSec = NCCI.get(Calendar.MINUTE) + (NCCI.get(Calendar.SECOND) /60);
                    if(minSec < 37.5){
                        NCCI.set(Calendar.MINUTE, 30);
                        NCCI.set(Calendar.SECOND, 0);
                        ats = NCCI.getTimeInMillis();
                        adjustData = "Interval Round";
                    }
                    else{
                        NCCI.set(Calendar.MINUTE, 45);
                        NCCI.set(Calendar.SECOND, 0);
                        ats = NCCI.getTimeInMillis();
                        adjustData = "Interval Round";
                    }
                }
                
                else if(NCCI.get(Calendar.MINUTE) > 45 && NCCI.get(Calendar.MINUTE) <= 59){
                    double minSec = NCCI.get(Calendar.MINUTE) + (NCCI.get(Calendar.SECOND) /60);
                    if(minSec < 52.5){
                        NCCI.set(Calendar.MINUTE, 45);
                        NCCI.set(Calendar.SECOND, 0);
                        ats = NCCI.getTimeInMillis();
                        adjustData = "Interval Round";
                    }
                    else{
                        NCCI.set(Calendar.HOUR_OF_DAY, (NCCI.get(Calendar.HOUR_OF_DAY) + 1));
                        NCCI.set(Calendar.MINUTE, 0);
                        NCCI.set(Calendar.SECOND, 0);
                        ats = NCCI.getTimeInMillis();
                        adjustData = "Interval Round";
                    }
                }
            }
       
    }

        // Getters
    

    public int getTerminalid() {
        return termId;
    }
    
    public Date getOriginalTimestamp(){
        return this.originalTimestamp;
    }
    
    public long getOTS(){
        return this.ots;
    }
    
    public long getATS(){
        return this.ats;
    }
    
    public int getPunchID() {
        return punchID;
    }

    public int getPunchType() {
        return this.punchType;
    }

    public String getStringTimestamp() {
        return this.stringTimestamp;
    }
      
    public String getBadgeID() {
        return Badge.getId();
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

    public String getName() {
        return Badge.getName();
    }

    public GregorianCalendar getGreg() {
        return greg;
    }
    public String getAdjustData(){
        return adjustData;
    }
    
    
    
    // Setters
    

    public void setTermID(int termId) {
        this.termId = termId;
    }

    public void setPunchID(int punchID) {
        this.punchID = punchID;
    }

    public void setStringTimestamp(String stringTimestamp) {
        this.stringTimestamp = stringTimestamp;
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

    public void setPunchType(int type) {
        this.punchType = type;
    }

    public void setGreg(GregorianCalendar greg) {
        this.greg = greg;
    }
    
        
}
 

