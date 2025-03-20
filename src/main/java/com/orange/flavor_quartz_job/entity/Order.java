package com.orange.flavor_quartz_job.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderId;
    private String date;
    @ElementCollection
    @CollectionTable(name = "order_flavors", joinColumns = @JoinColumn(name = "order_id"))
    private List<Chemicals> chemical = new ArrayList<>();
    private double totalAmount;
    private String status;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientInfo client;
}