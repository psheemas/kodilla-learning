package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {
    @Autowired
    private SimpleEmailService simpleEmailService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private AdminConfig adminConfig;

    private static final String SUBJECT = "Tasks: once a day mail";

    public String messageTemplate(){
        String message = "Currently in database You got "+ taskRepository.count();

        return taskRepository.count()>1 ? message + " tasks" : message + "task";

    }

    //@Scheduled(cron = "0 0 10 * * *")
    @Scheduled(fixedDelay = 100000000)
    public void sendInformationEmail(){
        long size = taskRepository.count();
        simpleEmailService.send(new Mail(
                adminConfig.getAdminMail(),
                SUBJECT,
                messageTemplate(),"no@mail"
                )
        );
    }
}
