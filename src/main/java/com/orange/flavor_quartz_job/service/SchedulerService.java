package com.orange.flavor_quartz_job.service;

import com.orange.flavor_quartz_job.entity.ClientInfo;
import com.orange.flavor_quartz_job.entity.SchedulerJobInfo;
import com.orange.flavor_quartz_job.enums.JobGroupEnums;
import com.orange.flavor_quartz_job.repo.ClientInfoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SchedulerService {
    @Autowired
    private ClientInfoRepository clientInfoRepository;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    public String deleteByJobName(String jobName) {
        boolean b;
        try {
            b = deleteJob(jobName);
            if (b) {
                return "success";
            }
        } catch (SchedulerException e) {
            return "failed";
        }
        return "failed";


    }

    @Transactional
    private boolean deleteJob(String jobInfo) throws SchedulerException {
        ClientInfo clientInfo = clientInfoRepository.findByJobName(jobInfo);
        clientInfoRepository.delete(clientInfo);
        log.info(">>>>> jobName = [" + jobInfo + "]" + " deleted.");
        return schedulerFactoryBean.getScheduler().deleteJob(new JobKey(jobInfo, JobGroupEnums.NOTIFICATION.name()));
    }
}
