package com.clasp.taskidoo.controller;

import com.clasp.taskidoo.entity.Task;
import com.clasp.taskidoo.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository repository;

    @BeforeEach
    public void setup() {
        repository.save(new Task("task1", LocalDateTime.now()));
        repository.save(new Task("task2", LocalDateTime.now().minusDays(1)));
    }

    @Test
    void findAll() throws Exception {
        this.mockMvc.perform(get("/v1/tasks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description", is("task2")));
    }

    @Test
    void findById() throws Exception {
        this.mockMvc.perform(get("/v1/tasks/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", is("task2")));
    }

    @Test
    void findByIdException() throws Exception {
        this.mockMvc.perform(get("/v1/tasks/-2"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void create() throws Exception {
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        var json = mapper.writeValueAsString( new Task("taskcreated", LocalDateTime.now()));
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/tasks")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", is("taskcreated")));
    }

    @Test
    void update() throws Exception{
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        var json = mapper.writeValueAsString( new Task("taskupdated", LocalDateTime.now()));
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/tasks/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", is("taskupdated")));


    }
}