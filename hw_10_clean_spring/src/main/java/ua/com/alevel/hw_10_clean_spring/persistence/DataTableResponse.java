package ua.com.alevel.hw_10_clean_spring.persistence;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class DataTableResponse<ENTITY> {

    private List<ENTITY> items;
    private long itemsSize;
    private Map<Object, Object> otherParamMap;
    private int currentPage;
    private int currentSize;
    private String sort;
    private String order;

    public DataTableResponse() {
        items = Collections.emptyList();
        otherParamMap = Collections.emptyMap();
        itemsSize = 0;
    }

}
