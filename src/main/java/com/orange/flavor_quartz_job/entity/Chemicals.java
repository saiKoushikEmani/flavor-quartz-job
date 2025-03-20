package com.orange.flavor_quartz_job.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Chemicals {
    private String name;
    private String quantity;
    private int price;
}