package util;

import java.util.List;

public class Calendar {
    private long timestamp;

    public Calendar() {
    }

    public Calendar(long timestamp) {
        if (timestamp < 0) {
            throw new IllegalArgumentException("Timestamp must be > 0");
        }
        this.timestamp = timestamp;
    }


    public long to(TimeType type) {
        return TimeType.MILLISECOND.to(timestamp, type);
    }

    public long get(TimeType type) {
        if (type.getPreviousType() == null) {
            return to(type);
        }
        return to(type) - type.getPreviousType().to(to(type.getPreviousType()), type) + 1;
    }

    public Calendar add(long time, TimeType type) {
        timestamp += type.to(time, TimeType.MILLISECOND);
        return this;
    }

    public Calendar subtract(long time, TimeType type) {
        timestamp -= type.to(time, TimeType.MILLISECOND);
        return this;
    }

    public Calendar interval(Calendar calendar) {
        long calendarMillis = calendar.to(TimeType.MILLISECOND);
        return new Calendar(timestamp - calendarMillis);
    }

    private void validateDate(List<Integer> dateList) {
        if (dateList.size() != 3) {
            throw new IllegalArgumentException("Date must have 3 , but have " + dateList.size());
        }

        Integer year = dateList.get(2);
        if (year < 0 || year > 4000) {
            throw new IllegalArgumentException(String.format("Year[%d] must be > 0 and < 4000", year));
        }

        Month month = Month.ofNumber(dateList.get(1));
        int day = dateList.get(0);
        int monthDays = month.getDays(year);
        if (day < 0 || day > monthDays) {
            throw new IllegalArgumentException(String.format("Day[%d] must be > 0 and < %d", day, monthDays));
        }
    }

    private void validateTime(List<Integer> timeList) {

    }

}
