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
import com.petproject.datask.utils.MessageConstant;
import com.petproject.datask.utils.ResponseHandler;

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
    public Object findAll() {
        return ResponseHandler.generateResponse(MessageConstant.SUCCESS.label, HttpStatus.OK, taskService.findAll());
    }

    @GetMapping("/{id}")
    public Object findByTaskId(@PathVariable String id) {
        try {
            Task task = taskService.findById(Long.valueOf(id));
            return ResponseHandler.generateResponse(MessageConstant.SUCCESS.label, HttpStatus.OK, task);
        } catch (Exception ex) {
            log.error("Failed to find task by id {} with exception {} {}.", id, ex.getMessage(), ex);
            return ResponseHandler.generateResponse(MessageConstant.RESOURCES_NOT_FOUND.label, HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping
    public Object add(@RequestBody TaskDTO dto) {
        try {
            taskService.add(dto);
            return ResponseHandler.generateResponse(MessageConstant.CREATED_SUCCESSFULLY.label, HttpStatus.CREATED);
        } catch (Exception ex) {
            log.error("Failed to add new task {} {}.", ex.getMessage(), ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public Object update(@RequestBody TaskDTO dto, @PathVariable String id) {
        try {
            taskService.update(dto, Long.valueOf(id));
            return ResponseHandler.generateResponse(MessageConstant.UPDATED_SUCCESSFULLY.label, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Failed to update task id {} with exception {} {}.", id, ex.getMessage(), ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public Object update(@PathVariable String id) {
        try {
            taskService.delete(Long.valueOf(id));
            return ResponseHandler.generateResponse(MessageConstant.DELETED_SUCCESSFULLY.label, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Failed to delete task id {} with exception {} {}.", id, ex.getMessage(), ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
