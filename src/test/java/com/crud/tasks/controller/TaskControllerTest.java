package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void getTasks() throws Exception {
        //Given
        List<TaskDto> listOfDtoTasks = new ArrayList<>();
        List<Task> listOfTasks = new ArrayList<>();
        TaskDto taskDto = new TaskDto(1990L,"task title", "task content");
        Task task = new Task(1990L,"task title", "task content");

        listOfTasks.add(task);
        listOfDtoTasks.add(taskDto);

        when(service.getAllTasks()).thenReturn(listOfTasks);
        when(taskMapper.mapToTaskDtoList(listOfTasks)).thenReturn(listOfDtoTasks);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)));
    }

    @Test
    public void getTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1990L,"task title", "task content");
        Task task = new Task(1990L,"task title", "task content");

        Optional<Task> optionalTask = Optional.of(new Task(1990L,"task title", "task content"));

        when(service.getTaskById(1990L)).thenReturn(optionalTask);
        when(taskMapper.mapToTaskDto(any())).thenReturn(taskDto);

        //When & Then
        mockMvc.perform(get("/v1/task/getTask?taskId=1990")
                //.param("taskId","1990")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1990)))
                .andExpect(jsonPath("$.title", is("task title")))
                .andExpect(jsonPath("$.content", is("task content")));
    }

    @Test
    public void deleteTask() throws Exception {
        //Given
        Optional<Task> task = Optional.of(new Task(1991L,"task_titele", "task_conent"));
        when(service.getTaskById(1990L)).thenReturn(task);

        //When & Then
        mockMvc.perform(delete("/v1/task/deleteTask")
                .param("taskId","1990")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(service).deleteTaskById(1990L);
    }

    @Test
    public void updateTask() throws Exception{
        //Given
        TaskDto taskDto = new TaskDto(1990L,"updated_title_task", "updated_content_task");
        Task task = new Task(1990L,"task title", "task content");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(service.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTaskDto(any())).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(1990)))
                .andExpect(jsonPath("$.title", is("updated_title_task")))
                .andExpect(jsonPath("$.content", is("updated_content_task")));
    }

    @Test
    public void createTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(null,"task title", "task content");
        Task task = new Task(null,"task title", "task content");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(service.saveTask(task)).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent));
    }
}