package org.hothub.calendarist.pojo;

import java.util.Calendar;
import java.util.Locale;

/**
 * 阳历日期
 */
public class SolarDate extends CalendaristDate {


    public SolarDate() {
    }

    public SolarDate(int year, int month, int day) {
        this(year, month, day, 0, 0, 0, 0);
    }

    public SolarDate(int year, int month, int day, int hour, int minute, int second, int millis) {
        super(year, month, day, hour, minute, second, millis);

        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(year, month - 1, day, hour, minute, second);
        calendar.set(Calendar.MILLISECOND, millis);

        this.timestamp = calendar.getTimeInMillis();
    }


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getMillis() {
        return millis;
    }

    public void setMillis(int millis) {
        this.millis = millis;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SolarDate{");
        sb.append("year=").append(year);
        sb.append(", month=").append(month);
        sb.append(", day=").append(day);
        sb.append(", hour=").append(hour);
        sb.append(", minute=").append(minute);
        sb.append(", second=").append(second);
        sb.append(", millis=").append(millis);
        sb.append(", timestamp=").append(timestamp);
        sb.append('}');
        return sb.toString();
    }
}
