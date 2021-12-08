package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleDateParser {
    private static final String DEFAULT_DATE_DELIMITER_PATTERN = "-";
    private static final String DATE_DELIMITER_PATTERN = "[-:/]";
    private static final String TIME_DELIMITER_PATTERN = ":";
    private static final String DATE_TIME_DELIMITER_PATTERN = " ";

    private final List<DataParsingType> dateTypeSequence;
    private final String dateDelimiter;

    public SimpleDateParser(String pattern) {
        validate(pattern);

        String[] dateTime = pattern.split(DATE_TIME_DELIMITER_PATTERN);
        String date = dateTime[0];
        String time = dateTime[1];

        List<String> dateTypes = List.of(date.split(DATE_DELIMITER_PATTERN));

        if (dateTypes.isEmpty()) {
            throw new IllegalArgumentException("");
        }

        dateDelimiter = dateTypes.size() == 1 ?
                DEFAULT_DATE_DELIMITER_PATTERN : pattern.substring(dateTypes.get(0).length(), dateTypes.get(0).length() + 1);

        List<DataParsingType> dataParsingTypes = List.of(DataParsingType.values());
        dateTypeSequence = new ArrayList<>(dataParsingTypes.size());

        Arrays.stream(DataParsingType.values())
                .map(dataParsingType -> Map.entry(dataParsingType, dateTypes.indexOf(dataParsingType.name().toLowerCase())))
                .filter(it -> it.getValue() >= 0)
                .sorted(Map.Entry.comparingByValue())
                .forEach(it -> dateTypeSequence.add(it.getKey()));

    }

    public Calendar parse(String date) {
        validate(date);

        String[] dateTime = date.split(DATE_TIME_DELIMITER_PATTERN);
        String timeString = dateTime[1];

        List<String> dateTypes = List.of(dateTime[0].split(dateDelimiter));

        if (dateTypes.isEmpty()) {
            throw new IllegalArgumentException("");
        }

        if (dateTypes.size() != dateTypeSequence.size()) {
            throw new IllegalArgumentException("");
        }

        Long millisecond = IntStream.range(0, dateTypeSequence.size())
                .mapToObj(index -> {
                    DataParsingType dataParsingType = dateTypeSequence.get(index);
                    long parseDate = dataParsingType.parse(dateTypes.get(index));

                    return dataParsingType.getType().to(normalizeTime(parseDate, dataParsingType), TimeType.MILLISECOND);
                }).reduce(0L, Long::sum);

        return new Calendar(millisecond);
    }

    private long normalizeTime(long parseDate, DataParsingType dataParsingType) {
        if (TimeType.YEAR.equals(dataParsingType.getType())) {
            return parseDate;
        }
        return parseDate > 0 ? parseDate - 1 : 0;
    }

    public String format(Calendar date) {
        return dateTypeSequence.stream()
                .map(dataParsingType -> dataParsingType.format(date.get(dataParsingType.getType())))
                .collect(Collectors.joining(dateDelimiter));
    }

    private void validate(String date) {
        if (date == null || date.strip().isEmpty()) {
            throw new IllegalArgumentException("");
        }
    }

    private enum DataParsingType {
        YYYY(TimeType.YEAR),
        MMM(TimeType.MONTH) {
            @Override
            public String format(long date) {
                Month month = Month.ofNumber((int) date);
                return month.name() + month.name().substring(1).toLowerCase();
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

        DataParsingType(TimeType type) {
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
}
