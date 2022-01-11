package ua.com.alevel.persistence.entity;

import lombok.Data;


@Data
public class Group {
    private long id;

    private String name;

    private long headman;
}
