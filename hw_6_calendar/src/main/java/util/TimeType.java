package util;

import java.util.stream.IntStream;

public enum TimeType {
    YEAR(12),
    MONTH(30.416666666f),
    DAY(24),
    HOUR(60),
    MINUTE(60),
    SECOND(1000),
    MILLISECOND(1);

    final float timeToNextType;

    TimeType(float timeToNextType) {
        this.timeToNextType = timeToNextType;
    }

    public long to(long time, TimeType targetType) {
        if (targetType.ordinal() < this.ordinal()) {
            return toUp((float) time, targetType);
        } else if (targetType.ordinal() > this.ordinal()) {
            return toDown((float) time, targetType);
        }

        return time;
    }

    private long toUp(float time, TimeType targetType) {
        TimeType previousType = getPreviousType();
        if (previousType == null || this.equals(targetType)) {
            return (long) time;
        }
        if (DAY.equals(this)) {
            int months = 0;
            int year = 0;
            Month month = Month.JANUARY;
            while (time >= 0) {
                time = time - month.getDays(year);
                if (time >= 0) {
                    months++;
                }
                if (months % 12 == 0) {
                    year++;
                }
                month = month.getNext() == null ? Month.JANUARY : month.getNext();
            }
            time = months;
        } else {
            time = time / previousType.timeToNextType;
        }

        return previousType.toUp(time, targetType);
    }

    private long toDown(float time, TimeType targetType) {
        TimeType nextType = getNextType();
        if (nextType == null || this.equals(targetType)) {
            return (long) time;
        }

        if (MONTH.equals(this)) {
            int clearMonth = (int) (time - (time % 12));
            float daysInAllYears = clearMonth * timeToNextType;
            int leapYears = ((clearMonth / 12) - ((clearMonth / 12) % 4)) / 4;

            int month = (int) time % 12;
            Integer daysInLastYear = IntStream.range(0, month)
                    .map(operand -> ++operand)
                    .mapToObj(Month::ofNumber)
                    .map(Month::getDays)
                    .reduce(0, Integer::sum);

            time = daysInLastYear + leapYears + daysInAllYears;
        } else {
            time = time * timeToNextType;
        }

        return nextType.toDown(time, targetType);
    }

    public TimeType getNextType() {
        return this.ordinal() >= values().length - 1 ? null : values()[this.ordinal() + 1];
    }

    public TimeType getPreviousType() {
        return this.ordinal() <= 0 ? null : values()[this.ordinal() - 1];
    }

}
