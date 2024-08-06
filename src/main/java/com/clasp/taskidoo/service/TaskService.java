package com.clasp.taskidoo.service;

import com.clasp.taskidoo.entity.Task;
import com.clasp.taskidoo.exception.TaskNotFoundException;
import com.clasp.taskidoo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskService {

    @Autowired
    TaskRepository repository;

    public List<Task> findAll() {
        Sort sortBy = Sort.by(new Sort.Order(Sort.Direction.ASC, "dueTime"));
        return repository.findAll(sortBy);
    }

    public Task findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task create(Task request){
        return repository.save(request);
    }

    public Task update(Task request , Long id) {
        return repository.findById(id)
                .map(
                        oldTask -> {
                            oldTask.setDescription(request.getDescription());
                            oldTask.setDueTime(request.getDueTime());
                            return repository.save(oldTask);
                        })
                .orElseGet(() -> repository.save(request));
    }

}
