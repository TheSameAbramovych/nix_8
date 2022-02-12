package ua.com.alevel.module_3.controller.dto;

import lombok.Data;

@Data
public class PageAndSizeData {
    int page;
    int size;
    long pageCount;
}
