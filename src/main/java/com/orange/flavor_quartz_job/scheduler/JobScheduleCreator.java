package com.orange.flavor_quartz_job.scheduler;

import com.orange.flavor_quartz_job.entity.ClientInfo;
import com.orange.flavor_quartz_job.jobs.SimpleJob;
import com.orange.flavor_quartz_job.model.request.ClientInfoRequest;
import com.orange.flavor_quartz_job.model.request.ScheduleNotification;
import com.orange.flavor_quartz_job.util.ClientInfoUtil;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.*;

@Component
public class JobScheduleCreator {
    public SimpleTrigger createSimpleTrigger(String triggerName, Date startTime, Long repeatTime, int misFireInstruction) {
        long repeatIntervalMillis = 2 * 24 * 60 * 60 * 1000L;
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setName(triggerName);
        factoryBean.setStartTime(startTime);
        factoryBean.setRepeatInterval(repeatIntervalMillis);
        factoryBean.setRepeatCount(5);
        factoryBean.setMisfireInstruction(misFireInstruction);
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    public SimpleTrigger createSimpleTrigger(ClientInfoRequest clientInfo) {
        String lastOrderDate = clientInfo.getLastOrderDate();
        ScheduleNotification scheduleNotification = clientInfo.getScheduleNotification();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Objects.requireNonNull(ClientInfoUtil.convertStringToDate(lastOrderDate)));
//        calendar.add(Calendar.DAY_OF_YEAR, scheduleNotification.getNoOfDaysAfterToReminder());
//        For testing
        calendar.add(Calendar.MINUTE, scheduleNotification.getNoOfDaysAfterToReminder());
        Date triggerStartTime = calendar.getTime();
        long repeatIntervalMillis = getRepeatIntervalInMillis(scheduleNotification);
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setName(clientInfo.getJobName());
        factoryBean.setStartTime(triggerStartTime);
        factoryBean.setRepeatInterval(repeatIntervalMillis);
        factoryBean.setRepeatCount(scheduleNotification.getRepeatCount());
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT);
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    private long getRepeatIntervalInMillis(ScheduleNotification scheduleNotification) {
        int repeatInterval = scheduleNotification.getRepeatIntervel();
        return switch (scheduleNotification.getRepeatIntervelUnit()) {
            case DAYS -> repeatInterval * 24 * 60 * 60 * 1000L; // Convert days to milliseconds
            case HOURS -> repeatInterval * 60 * 60 * 1000L; // Convert hours to milliseconds
            case MINUTES -> repeatInterval * 60 * 1000L; // Convert minutes to milliseconds
            case SECONDS -> repeatInterval * 1000L; // Convert seconds to milliseconds
            default ->
                    throw new IllegalArgumentException("Unsupported ChronoUnit: " + scheduleNotification.getRepeatIntervelUnit());
        };
    }

    public JobDetail createJob(ClientInfoRequest clientInfo, boolean isDurable,
                               ApplicationContext context) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(SimpleJob.class);
        factoryBean.setDurability(isDurable);
        factoryBean.setApplicationContext(context);
        factoryBean.setName(clientInfo.getJobName());
        factoryBean.setGroup(clientInfo.getJobGroup());

        // Set job data map
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobName", clientInfo.getJobName());
        jobDataMap.put("clientId", clientInfo.getClientId());
        jobDataMap.put("maxRetry", clientInfo.getScheduleNotification().getRepeatCount());

        factoryBean.setJobDataMap(jobDataMap);
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    public JobDetail createJob(Class<? extends QuartzJobBean> jobClass, boolean isDurable,
                               ApplicationContext context, String jobName, String jobGroup) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        factoryBean.setDurability(isDurable);
        factoryBean.setApplicationContext(context);
        factoryBean.setName(jobName);
        factoryBean.setGroup(jobGroup);

        // Set job data map
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobName", jobName);
        factoryBean.setJobDataMap(jobDataMap);
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }
}