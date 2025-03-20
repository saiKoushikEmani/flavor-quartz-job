package com.orange.flavor_quartz_job.model.response;

import com.orange.flavor_quartz_job.entity.Order;
import com.orange.flavor_quartz_job.model.request.Address;
import lombok.*;

import java.util.ArrayList;

@Data
public class ClientInfoResponse {
    private String clientId;
    private String name;
    private String jobName;
    private String jobGroup;
    private String status;

    private ArrayList<Order> orderHistory;

}