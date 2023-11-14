package com.petproject.datask.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petproject.datask.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
