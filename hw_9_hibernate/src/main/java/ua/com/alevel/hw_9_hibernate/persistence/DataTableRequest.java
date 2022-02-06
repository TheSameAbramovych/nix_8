package ua.com.alevel.hw_9_hibernate.persistence;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataTableRequest {

    private String sort;
    private String order;
    private int currentPage;
    private int pageSize;

    public DataTableRequest() {
    }

}
