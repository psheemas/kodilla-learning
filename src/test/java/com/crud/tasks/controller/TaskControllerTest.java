package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskController taskController;

    @Test
    public void getTasks() throws Exception {
        //Given
        List<TaskDto> listOfTasks = new ArrayList<>();
        TaskDto taskDto = new TaskDto(1990L,"task title", "task content");
        listOfTasks.add(taskDto);

        when(taskController.getTasks()).thenReturn(listOfTasks);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)));
    }

    @Test
    public void getTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1990L,"task title", "task content");

        when(taskController.getTask(1990L)).thenReturn(taskDto);

        //When & Then
        mockMvc.perform(get("/v1/task/getTask")
                .param("taskId","1990")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1990)))
                .andExpect(jsonPath("$.title",is("task title")))
                .andExpect(jsonPath("$.content",is("task content")));
    }

    @Test
    public void deleteTask() throws Exception {
        //Given
        List<TaskDto> listOfTasks = new ArrayList<>();
        TaskDto taskDto = new TaskDto(1990L,"task title", "task content");
        TaskDto taskDto1 = new TaskDto(1991L,"task title", "task content");
        listOfTasks.add(taskDto);
        listOfTasks.add(taskDto1);


        when(taskController.getTasks()).thenReturn(listOfTasks);
        when(taskController.deleteTask(1990L)).then(listOfTasks.remove(taskDto));


        do(listOfTasks.remove(taskDto)).when(taskController.deleteTask(1990L));
        doAnswer(listOfTasks.remove(taskDto)).when(taskController.deleteTask(1990L));

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)));


        mockMvc.perform(delete("/v1/task/deleteTask")
                .param("taskId","1990")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)));

    }

    @Test
    public void updateTask() {
    }

    @Test
    public void createTask() {
    }
}
