package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import net.minidev.json.JSONUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TaskControllerTest {
    @Autowired
    TaskController taskController;

    @Test
    public void getTasks() {

    }

    @Test
    public void getTask() {
    }

    @Test
    public void deleteTask() {
    }

    @Test
    public void updateTask() {
    }

    @Test
    public void createTask() {
        TaskDto taskDto = new TaskDto(1990L,"task_id","task_content");
        taskController.createTask(taskDto);

        taskController.getTasks().forEach(x->System.out.println(x.getTitle()));
        System.out.println("rozmiar bazy" + taskController.getTasks().size());
        Assert.assertEquals(taskController.getTasks().size(),11);

    }
}