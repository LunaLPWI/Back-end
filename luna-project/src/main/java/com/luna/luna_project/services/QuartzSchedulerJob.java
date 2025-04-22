package com.luna.luna_project.services;

import com.luna.luna_project.components.WhatsAppJob;
import com.luna.luna_project.models.Scheduling;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class QuartzSchedulerJob {

    @Autowired
    private Scheduler scheduler;

    public void agendarEnvio(Scheduling scheduling, String texto) throws SchedulerException {
        String nome = scheduling.getClient().getName();
        String numero = scheduling.getClient().getPhoneNumber();
        String horario = scheduling.getStartDateTime().toString();


        JobDetail jobDetail = JobBuilder.newJob(WhatsAppJob.class)
                .withIdentity("whatsappJob_" + scheduling.getId(), "whatsapp")
                .usingJobData("number", numero)
                .usingJobData("texto", texto)  // <--- AQUI
                .build();

        LocalDateTime envio = scheduling.getStartDateTime();
        ZonedDateTime zonedDateTime = envio.atZone(ZoneId.systemDefault());

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger_" + scheduling.getId(), "whatsapp")
                .startAt(Date.from(zonedDateTime.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

}

