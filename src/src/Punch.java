package src;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import src.Badge;
import java.util.Date;

public class Punch{
    
    private int idNum, year, month, day, hour, minute, second, mSecond;
    private Badge id;
    private String name;
    private GregorianCalendar date;
    
    public Punch(Badge inBadge, int yr, int mth, int inDay, int hr, int min, int sec, int ms){
        this.id = inBadge;
        this.year = yr;
        this.month = mth;
        this.day = inDay;
        this.hour = hr;
        this.minute = min;
        this.second = sec;
        this.mSecond = ms;
        
        this.name = inBadge.getName();
        this.idNum = inBadge.getId();
        
    }
    // Make Greg Calendar Object 
    
    public GregorianCalendar makeCalndar(int calYear, int calMonth, int calDay, int calHour, int calMinute, int calSecond) {
        
        GregorianCalendar date = new GregorianCalendar(calYear, calMonth, calDay,calHour, calMinute, calSecond);
        
      return date;
    }
    public Date makeDate(int calYear, int calMonth, int calDay, int calHour, int calMinute, int calSecond) {
        Date dateObj;
        dateObj = new Date (calYear, calMonth, calDay, calHour, calMinute, calSecond);
        return dateObj;
    }
    
    public long convertToMs (Date time) {
        
        long dateInMs = 0;
        
        dateInMs = time.getTime();
       
        return dateInMs;
    }
// Getters 
    
    public int getIdNum() {
        return idNum;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public int getmSecond() {
        return mSecond;
    }

    public Badge getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    // Setters

    public void setIdNum(int idNum) {
        this.idNum = idNum;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public void setmSecond(int mSecond) {
        this.mSecond = mSecond;
    }

    public void setId(Badge id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
