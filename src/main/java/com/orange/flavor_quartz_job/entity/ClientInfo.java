package com.orange.flavor_quartz_job.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ClientInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String clientId;
    private String name;
    @Column(unique = true, nullable = false)
    private String jobName;
    private String jobGroup;
    private String jobStatus;
    private String contactPerson;
    private String phone;
    private String email;
    @Embedded
    private Address address;
    private String businessType;
    @ElementCollection
    @CollectionTable(name = "client_flavors", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "chemicals")
    private List<String> preferredChemicals = new ArrayList<>();
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orderHistory = new ArrayList<>();
    private String lastOrderDate;
    private String notes;
    private int noOfDaysAfterToReminder;
    private int repeatCount;
}