package util;

public enum DataTypeParser implements TypeParser {
    YYYY(TimeType.YEAR),
    MMM(TimeType.MONTH) {
        @Override
        public String format(long date) {
            Month month = Month.ofNumber((int) date + 1);
            return month.name().charAt(0) + month.name().substring(1).toLowerCase();
        }

        @Override
        public long parse(String date) {
            return Month.valueOf(date.toUpperCase()).ordinal();
        }
    },
    MM(TimeType.MONTH) {
        @Override
        public String format(long date) {
            if (date % 10 != 0 && date < 10) {
                return "0" + super.format(date);
            }
            return super.format(date);
        }
    },
    M(TimeType.MONTH),
    DD(TimeType.DAY) {
        @Override
        public String format(long date) {
            if (date % 10 != 0 && date < 10) {
                return "0" + super.format(date);
            }
            return super.format(date);
        }
    },
    D(TimeType.DAY);

    private final TimeType type;

    DataTypeParser(TimeType type) {
        this.type = type;
    }

    public TimeType getType() {
        return type;
    }

    public long parse(String date) {
        if (date.isEmpty()) return 0;
        return Integer.parseInt(date);
    }

    public String format(long date) {
        return String.valueOf(date);
    }
}
