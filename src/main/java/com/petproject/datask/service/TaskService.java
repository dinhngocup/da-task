package com.petproject.datask.service;

import java.util.List;

import com.petproject.datask.dto.TaskDTO;
import com.petproject.datask.entity.Task;


public interface TaskService {
    List<Task> findAll();
    Task findById(Long id);
    void add(TaskDTO taskDTO);
    Task update(TaskDTO taskDTO, Long id);
    void delete(Long id);
}
