package com.orange.flavor_quartz_job.model.request;

import lombok.Data;

@Data
public class Address {
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
}