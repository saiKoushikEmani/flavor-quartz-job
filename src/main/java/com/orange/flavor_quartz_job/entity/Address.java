package com.orange.flavor_quartz_job.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Address {
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
}