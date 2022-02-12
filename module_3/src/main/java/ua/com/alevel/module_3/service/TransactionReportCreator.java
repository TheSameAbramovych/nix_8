package ua.com.alevel.module_3.service;

import java.util.Date;

public interface TransactionReportCreator {
    byte[] createCsvReport(Long userId, String wallet, Date from, Date to);
}
