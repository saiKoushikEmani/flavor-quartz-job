package com.orange.flavor_quartz_job.controller;

import com.orange.flavor_quartz_job.model.FlavorDistributorClintInfoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

//    @Autowired
//    private EmployeeRepository employeeRepository;

//    @Autowired
//    private EmployeeSchedulerService schedulerService;

//    @PostMapping("/add")
//    public String addEmployee(@RequestBody FlavorDistributorClintInfoRequest request) {
//        Employee employee = new Employee(request.getName(), request.getJoiningDate());
//        Employee savedEmployee = employeeRepository.save(employee);
//
//        try {
//            schedulerService.scheduleJob(savedEmployee.getId(), savedEmployee.getName(), savedEmployee.getJoiningDate(), request.getDaysAfter());
//            return "Employee " + savedEmployee.getName() + " added and event scheduled after " + request.getDaysAfter() + " days.";
//        } catch (Exception e) {
//            return "Error scheduling job: " + e.getMessage();
//        }
//    }
}

