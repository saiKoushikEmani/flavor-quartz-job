package com.orange.flavor_quartz_job.model.request;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ClientInfoRequest {
    private String clientId;
    private String name;
    private String jobName;
    private String jobGroup;
    private String contactPerson;
    private String phone;
    private String email;
    private Address address;
    private String businessType;
    private ArrayList<String> preferredChemicals;
    private String lastOrderDate;
    private String notes;

    private ScheduleNotification scheduleNotification;


}