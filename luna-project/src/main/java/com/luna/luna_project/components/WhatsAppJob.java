package com.luna.luna_project.components;

import com.luna.luna_project.services.WhatsAppService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class WhatsAppJob extends QuartzJobBean {


    @Override
    protected void executeInternal(JobExecutionContext context) {
        JobDataMap dataMap = context.getMergedJobDataMap();

        String number = dataMap.getString("number");
        String texto = dataMap.getString("texto");

        WhatsAppService.getInstance().enviarMensagem(texto, number);
    }

}
