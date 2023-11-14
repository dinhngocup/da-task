package com.petproject.datask.dto;

import lombok.Data;

@Data
public class TaskDTO {
    private String title;
    private String description;
    private Boolean completed;
}
