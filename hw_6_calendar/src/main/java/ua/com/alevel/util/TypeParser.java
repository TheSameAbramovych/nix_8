package ua.com.alevel.util;

public interface TypeParser {

    TimeType getType();
    long parse(String time);
    String format(long date);
}
