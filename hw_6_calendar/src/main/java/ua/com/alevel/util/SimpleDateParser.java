package ua.com.alevel.util;

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

    private final List<TypeParser> sequenceDateParser;
    private List<TypeParser> sequenceTimeParser;
    private final String dateDelimiter;

    public SimpleDateParser(String pattern) {
        validate(pattern);

        String[] dateTime = pattern.split(DATE_TIME_DELIMITER_PATTERN);
        List<String> date = List.of(dateTime[0].split(DATE_DELIMITER_PATTERN));
        if (date.isEmpty()) {
            throw new IllegalArgumentException("Неккоректный ввод!");
        }

        dateDelimiter = date.size() == 1 ?
                DEFAULT_DATE_DELIMITER_PATTERN : pattern.substring(date.get(0).length(), date.get(0).length() + 1);
        sequenceDateParser = new ArrayList<>(date.size());
        Arrays.stream(DataTypeParser.values())
                .map(dataParsingType -> Map.entry(dataParsingType, date.indexOf(dataParsingType.name().toLowerCase())))
                .filter(it -> it.getValue() >= 0)
                .sorted(Map.Entry.comparingByValue())
                .forEach(it -> sequenceDateParser.add(it.getKey()));

        if (dateTime.length > 1) {
            List<String> time = List.of(dateTime[1].split(TIME_DELIMITER_PATTERN));
            if (time.isEmpty()) {
                throw new IllegalArgumentException("Список пуст!");
            }
            sequenceTimeParser = new ArrayList<>(time.size());
            Arrays.stream(TimeTypeParser.values())
                    .limit(time.size())
                    .forEach(sequenceTimeParser::add);
        }
    }

    public Calendar parse(String dateString) {
        validate(dateString);

        String[] dateTime = dateString.split(DATE_TIME_DELIMITER_PATTERN);
        List<String> date = List.of(dateTime[0].split(dateDelimiter));

        if (date.isEmpty() || date.size() != sequenceDateParser.size()) {
            throw new IllegalArgumentException("Неккоректный ввод!");
        }
        Long timestamp = convertToAndSum(date, sequenceDateParser);

        if (dateTime.length > 1) {
            List<String> time = List.of(dateTime[1].split(TIME_DELIMITER_PATTERN));
            if (time.isEmpty() || time.size() != sequenceTimeParser.size()) {
                throw new IllegalArgumentException("Неккоректный ввод!");
            }
            timestamp += convertToAndSum(time, sequenceTimeParser);
        }

        return new Calendar(timestamp);
    }

    public String format(Calendar date) {
        String dateString = sequenceDateParser.stream()
                .map(dataTypeParser -> dataTypeParser.format(date.get(dataTypeParser.getType())))
                .collect(Collectors.joining(dateDelimiter));

        if (sequenceTimeParser != null) {
            String timeString = sequenceTimeParser.stream()
                    .map(dataTypeParser -> dataTypeParser.format(date.get(dataTypeParser.getType())))
                    .collect(Collectors.joining(TIME_DELIMITER_PATTERN));
            dateString += DATE_TIME_DELIMITER_PATTERN + timeString;
        }

        return dateString;
    }

    private long normalize(long parseDate, TypeParser parser) {
        if (TimeType.MONTH.equals(parser.getType()) ||
                TimeType.DAY.equals(parser.getType())) {
            return parseDate > 0 ? parseDate - 1 : 0;
        }
        return parseDate;
    }

    private Long convertToAndSum(List<String> time, List<TypeParser> typeParsers) {
        return IntStream.range(0, typeParsers.size())
                .mapToObj(index -> {
                    TypeParser typeParser = typeParsers.get(index);
                    long parseDate = typeParser.parse(time.get(index));
                    return typeParser.getType().to(normalize(parseDate, typeParser), TimeType.MILLISECOND);
                }).reduce(0L, Long::sum);
    }

    private void validate(String date) {
        if (date == null || date.strip().isEmpty()) {
            throw new IllegalArgumentException("Неккоректный ввод!");
        }
    }

}
