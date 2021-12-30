package ua.com.alevel.util;

public enum Month {

    JANUARY(31),
    FEBRUARY(28),
    MARCH(31),
    APRIL(30),
    MAY(31),
    JUNE(30),
    JULY(31),
    AUGUST(31),
    SEPTEMBER(30),
    OCTOBER(31),
    NOVEMBER(30),
    DECEMBER(31);

    private final int days;

    Month(int days) {
        this.days = days;
    }

    public int getDays(int year) {
        if (Month.FEBRUARY.equals(this)) {
            if (year == 0) {
                return getDays();
            }
            if (year % 400 == 0) {
                return this.getDays() + 1;
            }
            return year % 4 == 0 && !(year % 100 == 0) ? this.getDays() + 1 : this.getDays();
        }
        return this.getDays();
    }

    public int getDays() {
        return days;
    }

    public static Month ofNumber(int i) {
        if (i > 12) {
            throw new IllegalArgumentException("Не найден месяц по номеру " + i);
        }
        return values()[i - 1];
    }

    public Month getNext() {
        return this.ordinal() >= values().length - 1 ? null : values()[this.ordinal() + 1];
    }
}
