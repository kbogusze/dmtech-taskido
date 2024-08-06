package com.clasp.taskidoo.controller;

import com.clasp.taskidoo.entity.Task;
import com.clasp.taskidoo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/tasks")
public class TaskController {

    @Autowired
    TaskService service;

    @GetMapping
    public List<Task> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    public Task findById(@PathVariable Long id){
        return service.findById(id);
    }

    @PostMapping
    public Task create(@RequestBody Task request){
        return service.create(request);
    }

    @PutMapping(value = "/{id}")
    public Task update(@RequestBody Task request , @PathVariable Long id) {
        return service.update(request,id);
    }
}
