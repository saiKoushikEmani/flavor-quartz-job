package com.orange.flavor_quartz_job.scheduler;

import com.orange.flavor_quartz_job.entity.Address;
import com.orange.flavor_quartz_job.entity.ClientInfo;
import com.orange.flavor_quartz_job.jobs.SimpleJob;
import com.orange.flavor_quartz_job.model.request.ClientInfoRequest;
import com.orange.flavor_quartz_job.repo.ClientInfoRepository;
import com.orange.flavor_quartz_job.enums.JobStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

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
                JobDetail jobDetail = scheduleCreator.createJob(clientInfoRequest, true, context);

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

//    private void updateScheduleJob(ClientInfoRequest clientInfoRequest) {
//        Trigger newTrigger;
//        newTrigger = scheduleCreator.createSimpleTrigger(clientInfoRequest);
//        try {
//            schedulerFactoryBean.getScheduler().rescheduleJob(TriggerKey.triggerKey(clientInfoRequest.getJobName()), newTrigger);
//            ClientInfo clientInfo = buildClientInfo(clientInfoRequest);
//            clientInfoRepository.save(clientInfo);
//            log.info(">>>>> jobName = [" + clientInfoRequest.getJobName() + "]" + " updated and scheduled.");
//        } catch (SchedulerException e) {
//            log.error(e.getMessage(), e);
//        }
//    }

    private ClientInfo buildClientInfo(ClientInfoRequest clientInfoRequest) {
        Address address = new Address();
        address.setCity(clientInfoRequest.getAddress().getCity());
        address.setState(clientInfoRequest.getAddress().getState());
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setNoOfDaysAfterToReminder(clientInfo.getNoOfDaysAfterToReminder());
        clientInfo.setClientId(clientInfoRequest.getClientId());
        clientInfo.setJobName(clientInfoRequest.getJobName());
        clientInfo.setJobGroup(clientInfoRequest.getJobGroup());
        clientInfo.setName(clientInfoRequest.getName());
        clientInfo.setEmail(clientInfoRequest.getEmail());
        clientInfo.setPhone(clientInfoRequest.getPhone());
        clientInfo.setContactPerson(clientInfoRequest.getContactPerson());
        clientInfo.setBusinessType(clientInfoRequest.getBusinessType());
        clientInfo.setLastOrderDate(clientInfoRequest.getLastOrderDate());
        clientInfo.setPreferredChemicals(clientInfoRequest.getPreferredChemicals());
        clientInfo.setNotes(clientInfoRequest.getNotes());
        clientInfo.setAddress(address);
        return clientInfo;
    }

}
