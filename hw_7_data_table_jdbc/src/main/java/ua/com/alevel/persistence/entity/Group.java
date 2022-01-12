package ua.com.alevel.persistence.entity;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
public class Group {
    @NotNull
    private long id;
    @NotEmpty(message = "First name is empty")
    private String name;
    @Min(value = 1, message = "Id can`t be less than 1")
    private long headman;
}
