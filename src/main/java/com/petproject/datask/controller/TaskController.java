package com.petproject.datask.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petproject.datask.dto.TaskDTO;
import com.petproject.datask.entity.Task;
import com.petproject.datask.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final Logger log = LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> findAll() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public Object findByTaskId(@PathVariable String id) {
        try {
            Task task = taskService.findById(Long.valueOf(id));
            return task;
        } catch (Exception ex) {
            log.error("Failed to find task by id {} with exception {} {}.", id, ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping
    public Object add(@RequestBody TaskDTO dto) {
        try {
            taskService.add(dto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Failed to add new task {} {}.", ex.getMessage(), ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public Object update(@RequestBody TaskDTO dto, @PathVariable String id) {
        try {
            Task updatedTask = taskService.update(dto, Long.valueOf(id));
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Failed to update task id {} with exception {} {}.", id, ex.getMessage(), ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public Object update(@PathVariable String id) {
        try {
            taskService.delete(Long.valueOf(id));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Failed to delete task id {} with exception {} {}.", id, ex.getMessage(), ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
