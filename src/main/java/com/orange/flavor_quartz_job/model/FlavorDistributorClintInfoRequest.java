package com.orange.flavor_quartz_job.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FlavorDistributorClintInfoRequest {
    private String clientName;
    private LocalDate purchasedDate;
    private int daysAfterRemainder;

    private String description;
    private List<Metadata> metadataList;


}
