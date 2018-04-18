package tools;

import java.util.Date;

public class Constants {

    public static Date setWeek(){
        Date date = new Date();
        date.setTime(date.getTime() + 7 * 24 * 60 * 60 * 1000);
        return date;
    }

    public static Date setTwoWeeks(){
        Date date = new Date();
        date.setTime(date.getTime() + 14 * 24 * 60 * 60 * 1000);
        return date;
    }

    public static Date setThreeWeeks(){
        Date date = new Date();
        date.setTime(date.getTime() + 21 * 24 * 60 * 60 * 1000);
        return date;
    }

    public static Date setFourWeeks(){
        Date date = new Date();
        date.setTime(date.getTime() + 28L * 24 * 60 * 60 * 1000);
        return date;
    }

    public static Date setWeek(Date date){
        date.setTime(date.getTime() + 7 * 24 * 60 * 60 * 1000);
        return date;
    }

    public static Date setTwoWeeks(Date date){
        date.setTime(date.getTime() + 14 * 24 * 60 * 60 * 1000);
        return date;
    }

    public static Date setThreeWeeks(Date date){
        date.setTime(date.getTime() + 21 * 24 * 60 * 60 * 1000);
        return date;
    }

    public static Date setFourWeeks(Date date){
        date.setTime(date.getTime() + 28L * 24 * 60 * 60 * 1000);
        return date;
    }
}
