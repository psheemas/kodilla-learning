package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void getTasks() throws Exception {
        //Given
        List<TaskDto> listOfTasksDto = new ArrayList<>();
        TaskDto task = new TaskDto(1990L,"task_title","task_content");
        listOfTasksDto.add(task);

        when(taskMapper.mapToTaskDtoList(dbService.getAllTasks())).thenReturn(listOfTasksDto);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)));
    }

    @Test
    public void getTask() throws Exception {
        //Given
        TaskDto taskdto = new TaskDto(1990L,"task_title","task_content");
        Task task = new Task(1990L,"task_title","task_content");

        when(taskMapper.mapToTaskDto(dbService.getTaskById(1990L).orElseThrow(TaskNotFoundException::new))).thenReturn(taskdto);

        //When & Then
        mockMvc.perform(get("/v1/task/getTask")
                .param("taskId","1990")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1990)))
                .andExpect(jsonPath("$.title",is("task title")))
                .andExpect(jsonPath("$.content",is("task content")));
    }
}
