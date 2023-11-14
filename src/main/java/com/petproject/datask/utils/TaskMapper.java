package com.petproject.datask.utils;

import com.petproject.datask.dto.TaskDTO;
import com.petproject.datask.entity.Task;

public class TaskMapper {
    public static Task mapDtoToEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(taskDTO.getCompleted());
        return task;
    }
}
