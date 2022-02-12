package ua.com.alevel.module_3.dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LimitedRequest {

    private String sort;
    private String order;
    private int currentPage;
    private int pageSize;

    public LimitedRequest() {
    }

}
