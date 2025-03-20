package com.orange.flavor_quartz_job.scheduler;

import com.orange.flavor_quartz_job.entity.Address;
import com.orange.flavor_quartz_job.entity.ClientInfo;
import com.orange.flavor_quartz_job.entity.SchedulerJobInfo;
import com.orange.flavor_quartz_job.jobs.SimpleJob;
import com.orange.flavor_quartz_job.model.request.ClientInfoRequest;
import com.orange.flavor_quartz_job.repo.ClientInfoRepository;
import com.orange.flavor_quartz_job.repo.SchedulerRepository;
import com.orange.flavor_quartz_job.util.JobStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class ClientInfoScheduler {
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private ClientInfoRepository clientInfoRepository;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private JobScheduleCreator scheduleCreator;

    public void scheduleNewJob(ClientInfo jobInfo, ClientInfoRequest clientInfoRequest) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            JobKey jobKey = new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup());
            if (!scheduler.checkExists(jobKey)) {
                JobDetail jobDetail = scheduleCreator.createJob(
                        SimpleJob.class, false, context,
                        jobInfo.getJobName(), jobInfo.getJobGroup());

                Trigger trigger = scheduleCreator.createSimpleTrigger(clientInfoRequest);

                scheduler.scheduleJob(jobDetail, trigger);

                jobInfo.setJobStatus(JobStatusEnum.NEW.name());

                clientInfoRepository.save(jobInfo);

                log.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " scheduled.");
            } else {
                log.error("job already exist");
                throw new RuntimeException("job already exist");
            }
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }

}
