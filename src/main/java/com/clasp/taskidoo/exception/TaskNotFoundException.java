package com.clasp.taskidoo.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(Long id) {
        super("Could not find task " + id);
    }
}
