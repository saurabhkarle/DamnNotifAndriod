package sih.firebasesendnotif.Classes;

/**
 * Created by gaurav on 3/25/2018.
 */

public class NotifyData
{
    String date;
    String time;
    String duration;
    String city_name;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public NotifyData(String date, String time, String duration, String city_name) {

        this.date = date;
        this.time = time;
        this.duration = duration;
        this.city_name = city_name;
    }
}
