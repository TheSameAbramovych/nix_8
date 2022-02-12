package ua.com.alevel.module_3.controller.dto;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public enum ReportPeriod {
    DAY(ChronoUnit.DAYS),
    WEEK(ChronoUnit.WEEKS),
    MONTH(ChronoUnit.MONTHS),
    YEAR(ChronoUnit.YEARS);

    private final ChronoUnit unit;

    ReportPeriod(ChronoUnit unit) {
        this.unit = unit;
    }

    public Date getFrom(Date to) {
        return Date.from(LocalDateTime.ofInstant(to.toInstant(), ZoneOffset.UTC).minus(1, unit).toInstant(ZoneOffset.UTC));
    }

    public String getName() {
        return StringUtils.capitalize(name().toLowerCase());
    }
}
