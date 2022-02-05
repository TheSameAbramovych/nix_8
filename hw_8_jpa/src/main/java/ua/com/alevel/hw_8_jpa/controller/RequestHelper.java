package ua.com.alevel.hw_8_jpa.controller;

import org.springframework.util.StringUtils;
import ua.com.alevel.hw_8_jpa.controller.dto.PageAndSizeData;
import ua.com.alevel.hw_8_jpa.controller.dto.SortData;

import java.util.List;

public class RequestHelper {
    public static PageAndSizeData getValidPageAndSizeData(PageAndSizeData pageAndSizeData) {
        if (pageAndSizeData == null || pageAndSizeData.getSize() <= 0 || pageAndSizeData.getPage() <= 0) {
            pageAndSizeData = new PageAndSizeData();
            pageAndSizeData.setPage(1);
            pageAndSizeData.setSize(10);
        }
        return pageAndSizeData;
    }

    public static SortData getValidSortData(SortData sortData) {
        if (sortData == null || !StringUtils.hasLength(sortData.getSort()) ||
                !StringUtils.hasLength(sortData.getOrder()) || !List.of("ASC", "DESC").contains(sortData.getOrder().toUpperCase())) {
            sortData = new SortData();
            sortData.setSort("id");
            sortData.setOrder("ASC");
        }
        return sortData;
    }
}
