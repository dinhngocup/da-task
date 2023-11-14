package com.petproject.datask.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petproject.datask.dto.TaskDTO;
import com.petproject.datask.entity.Task;
import com.petproject.datask.repository.TaskRepository;
import com.petproject.datask.service.TaskService;
import com.petproject.datask.utils.TaskMapper;

@Service
public class TaskServiceImpl implements TaskService {
    TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    /**
     * Get All Tasks.
     *
     * @return List<Task>
     */
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    /**
     * Get Task by ID.
     *
     * @return Task
     */
    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow();
    }

    /**
     * Add New Task.
     *
     * @param taskDTO The new data for the task.
     */
    public void add(TaskDTO taskDTO) {
        taskRepository.save(TaskMapper.mapDtoToEntity(taskDTO));
    }

    /**
     * Update New Task.
     *
     * @param taskDTO The updated data for the task.
     * @param id The updated task id.
     * @return Task
     */
    public Task update(TaskDTO taskDTO, Long id) {
        Task task = TaskMapper.mapDtoToEntity(taskDTO);
        task.setId(id);
        return taskRepository.save(task);
    }

    /**
     * Delete Current Task.
     */
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
