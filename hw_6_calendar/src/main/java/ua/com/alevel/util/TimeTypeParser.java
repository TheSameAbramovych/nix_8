package ua.com.alevel.util;

public enum TimeTypeParser implements TypeParser {
    HOUR(TimeType.HOUR, 24),
    MINUTE(TimeType.MINUTE, 60),
    SECOND(TimeType.SECOND, 60),
    MILLISECOND(TimeType.MILLISECOND, 1000);

    private final TimeType type;
    private final int maxValue;

    TimeTypeParser(TimeType type, int maxValue) {
        this.type = type;
        this.maxValue = maxValue;
    }

    @Override
    public TimeType getType() {
        return type;
    }

    @Override
    public long parse(String time) {
        if (time.isEmpty()) return 0;
        int i = Integer.parseInt(time);
        validate(i);
        return i;
    }

    private void validate(long time) {
        if (time >= maxValue) {
            throw new IllegalArgumentException("Превышен лимит!");
        }
    }

    @Override
    public String format(long date) {
        return String.valueOf(date);
    }
}
