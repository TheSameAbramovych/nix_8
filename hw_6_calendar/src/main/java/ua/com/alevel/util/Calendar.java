package ua.com.alevel.util;

import java.util.List;

public class Calendar {
    private long timestamp;

    public Calendar() {
    }

    public Calendar(long timestamp) {
        if (timestamp < 0) {
            throw new IllegalArgumentException("Timestamp должен быть > 0");
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
        if (TimeType.DAY.equals(type) || TimeType.MONTH.equals(type)) {
            return to(type) - type.getPreviousType().to(to(type.getPreviousType()), type) + 1;
        }
        return to(type) - type.getPreviousType().to(to(type.getPreviousType()), type);
    }

    public Calendar add(long time, TimeType type) {
        timestamp += type.to(time, TimeType.MILLISECOND);
        return this;
    }

    public Calendar subtract(long time, TimeType type) {
        timestamp -= type.to(time, TimeType.MILLISECOND);
        return this;
    }

    public Calendar subtract(Calendar calendar) {
        return new Calendar(this.timestamp - calendar.timestamp);
    }

    public Calendar interval(Calendar calendar) {
        long calendarMillis = calendar.to(TimeType.MILLISECOND);
        return new Calendar(timestamp - calendarMillis);
    }

    private void validateDate(List<Integer> dateList) {
        if (dateList.size() != 3) {
            throw new IllegalArgumentException("Дата должна состоять с 3 елементов , но состоит с " + dateList.size());
        }

        Integer year = dateList.get(2);
        if (year < 0 || year > 4000) {
            throw new IllegalArgumentException(String.format("Нарушен интервал > 0 и < 4000", year));
        }

        Month month = Month.ofNumber(dateList.get(1));
        int day = dateList.get(0);
        int monthDays = month.getDays(year);
        if (day < 0 || day > monthDays) {
            throw new IllegalArgumentException(String.format("Дни[%d] должны быть > 0 и < %d", day, monthDays));
        }
    }

    private void validateTime(List<Integer> timeList) {

    }

    public int compareTo(Calendar a) {
        if (this.timestamp < a.timestamp) {
            return -1;
        } else {
            return 1;
        }
    }

    public int compareT(Calendar a) {
        if (this.timestamp < a.timestamp) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        SimpleDateParser parser = new SimpleDateParser("dd/mm/yyyy");
        String a = parser.format(new Calendar(timestamp));
        return "Calendar {" +
                a +
                '}';
    }
}
