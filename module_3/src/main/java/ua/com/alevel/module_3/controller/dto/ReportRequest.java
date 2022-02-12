package ua.com.alevel.module_3.controller.dto;

import lombok.Data;

@Data
public class ReportRequest {
    private Long userId;
    private String wallet;
    private ReportPeriod period;
}
