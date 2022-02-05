package ua.com.alevel.hw_8_jpa.controller.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class CreateGroupRequest {
    @NotEmpty(message = "Name is empty")
    private String name;

    @Min(value = 1, message = "Student can`t be null")
    private long headmanId;
}
