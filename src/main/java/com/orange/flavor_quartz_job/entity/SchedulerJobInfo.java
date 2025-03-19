package com.orange.flavor_quartz_job.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class SchedulerJobInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int jobId;
    private String jobName;
    private String jobGroup;
    private String jobStatus;
    private String jobClass;
    private String cronExpression;
    @Column(name = "description")
    private String desc;    
    private String interfaceName;
    private Long repeatTime;
    private Boolean cronJob;
}