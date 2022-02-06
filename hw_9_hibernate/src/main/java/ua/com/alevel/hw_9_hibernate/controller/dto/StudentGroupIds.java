package ua.com.alevel.hw_9_hibernate.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StudentGroupIds {

    @NotNull(message = "Id can't be null")
    private Long studentId;

    @NotNull(message = "Id can't be null")
    private Long groupId;
}
