package com.orange.flavor_quartz_job.jobs;

import com.orange.flavor_quartz_job.entity.ClientInfo;
import com.orange.flavor_quartz_job.enums.JobStatusEnum;
import com.orange.flavor_quartz_job.repo.ClientInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.stream.IntStream;

@Slf4j

public class SimpleJob extends QuartzJobBean {
    @Autowired
    private ClientInfoRepository clientInfoRepository;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("SimpleJob Start................");

        JobDataMap jobDataMap = context.getMergedJobDataMap();
        String clientId = (String) jobDataMap.get("clientId");
        String jobName = (String) jobDataMap.get("jobName");

        ClientInfo clientInfo = clientInfoRepository.findByJobNameAndClientId(jobName, clientId).orElseThrow(() -> new RuntimeException("ClientId: " + clientId + " and JobName: " + jobName + " missing in DB table "));
        int incrementalRepeatCount = clientInfo.getRepeatCount() + 1;
        if (incrementalRepeatCount == 5) {
            clientInfoRepository.updateJobStatusAndRepeatCountByClientIdAndJobName(JobStatusEnum.COMPLETED.name(), incrementalRepeatCount, clientId, jobName);
        } else {
            clientInfoRepository.updateJobStatusAndRepeatCountByClientIdAndJobName(JobStatusEnum.IN_PROCESS.name(), incrementalRepeatCount, clientId, jobName);
        }


        log.info("SimpleJob End....Notification Sent successfully ............");
    }
}