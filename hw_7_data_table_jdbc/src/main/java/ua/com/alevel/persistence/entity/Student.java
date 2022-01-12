package ua.com.alevel.persistence.entity;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class Student {
    private long id;

    @NotEmpty(message = "First name is empty")
    private String firstName;

    @NotEmpty(message = "Last name is empty")
    private String lastName;

    @Min(value = 1, message = "Age can`t be less than 1")
    @Max(value = 100, message = "Age can`t be more than 100")
    private int age;

    @NotEmpty(message = "Email is empty")
    @Email(message = "Email is incorrect")
    private String email;
}
