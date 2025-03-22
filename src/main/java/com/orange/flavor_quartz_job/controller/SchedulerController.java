package com.orange.flavor_quartz_job.controller;

import com.orange.flavor_quartz_job.service.SchedulerService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("scheduler")
public class SchedulerController {

    private SchedulerService schedulerService;

    @DeleteMapping(path = "jobName/{jobName}")
    public String deleteByJobName(@PathVariable String jobName) {
        return schedulerService.deleteByJobName(jobName);

    }
}
