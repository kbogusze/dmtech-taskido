package com.clasp.taskidoo.repository;

import com.clasp.taskidoo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
