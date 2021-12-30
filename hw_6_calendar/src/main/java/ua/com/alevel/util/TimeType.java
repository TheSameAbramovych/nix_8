package ua.com.alevel.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.IntStream;

public enum TimeType {

    YEAR(12),
    MONTH(30.416666666),
    DAY(24),
    HOUR(60),
    MINUTE(60),
    SECOND(1000),
    MILLISECOND(1);

    final BigDecimal timeToNextType;

    TimeType(double timeToNextType) {
        this.timeToNextType = new BigDecimal(timeToNextType);
    }

    public long to(long time, TimeType targetType) {
        if (targetType.ordinal() < this.ordinal()) {
            return toUp(new BigDecimal(time), targetType);
        } else if (targetType.ordinal() > this.ordinal()) {
            return toDown(new BigDecimal(time), targetType);
        }

        return time;
    }

    private long toUp(BigDecimal time, TimeType targetType) {
        TimeType previousType = getPreviousType();
        if (previousType == null || this.equals(targetType)) {
            return time.longValue();
        }
        if (DAY.equals(this)) {
            int months = 0;
            int year = 0;
            Month month = Month.JANUARY;
            while (time.compareTo(BigDecimal.ZERO) >= 0) {
                time = time.subtract(new BigDecimal(month.getDays(year)));
                if (time.compareTo(BigDecimal.ZERO) >= 0) {
                    months++;
                }
                if (months % 12 == 0) {
                    year++;
                }
                month = month.getNext() == null ? Month.JANUARY : month.getNext();
            }
            time = new BigDecimal(months);
        } else {
            time = time.divide(previousType.timeToNextType, RoundingMode.DOWN);
        }

        return previousType.toUp(time, targetType);
    }

    private long toDown(BigDecimal time, TimeType targetType) {
        TimeType nextType = getNextType();
        if (nextType == null || this.equals(targetType)) {
            return time.longValue();
        }

        if (MONTH.equals(this)) {
            BigDecimal monthCount = new BigDecimal(12);
            BigDecimal clearMonth = time.subtract(time.remainder(monthCount));
            BigDecimal leapYears = clearMonth.divide(monthCount, RoundingMode.DOWN).divide(new BigDecimal(4), RoundingMode.DOWN);

            BigDecimal daysInAllYears = timeToNextType.multiply(clearMonth).setScale(1, RoundingMode.UP);
            int month = time.remainder(monthCount).intValue();
            Integer daysInLastYear = IntStream.range(0, month)
                    .map(operand -> ++operand)
                    .mapToObj(Month::ofNumber)
                    .map(Month::getDays)
                    .reduce(0, Integer::sum);

            time = new BigDecimal(daysInLastYear).add(leapYears).add(daysInAllYears).setScale(1, RoundingMode.UP);
        } else {
            time = time.multiply(timeToNextType);
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
