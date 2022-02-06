package ua.com.alevel.hw_9_hibernate.controller.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class CreateGroupRequest {
    @NotEmpty(message = "Name can't be empty")
    private String name;

    @Min(value = 1, message = "Student not found")
    private long headmanId;
}
