package ua.com.alevel.hw_8_9_jpa_hibernate.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StudentGroupIds {

    @NotNull(message = "Id is null")
    private Long studentId;

    @NotNull(message = "Id is null")
    private Long groupId;
}
