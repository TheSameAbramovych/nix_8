package ua.com.alevel.persistence.entity;

import lombok.Data;


@Data
public class Student {
    private long id;
    private String firstName;
    private String lastName;

    private int age;
    private String email;
}
