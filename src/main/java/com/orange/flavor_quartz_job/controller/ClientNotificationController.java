package com.orange.flavor_quartz_job.controller;

import com.orange.flavor_quartz_job.model.request.ClientInfoRequest;
import com.orange.flavor_quartz_job.model.response.ClientInfoResponse;
import com.orange.flavor_quartz_job.service.ClientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clientInfo")
public class ClientNotificationController {


    @Autowired
    private ClientInfoService clientInfoService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ClientInfoResponse addClientInfo(@RequestBody ClientInfoRequest request) throws Exception {
        return clientInfoService.addClientInfo(request);

    }

    @DeleteMapping
    public String deleteAll() throws Exception {
        return clientInfoService.deleteAll();

    }
}

