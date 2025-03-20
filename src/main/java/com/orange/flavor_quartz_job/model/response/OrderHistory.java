package com.orange.flavor_quartz_job.model.response;

import lombok.Data;

import java.util.ArrayList;

@Data
public class OrderHistory {
    private String orderId;
    private String date;
    private ArrayList<Flavor> flavors;
    private int totalAmount;
    private String status;
}