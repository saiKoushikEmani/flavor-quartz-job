package com.orange.flavor_quartz_job.service;

import com.orange.flavor_quartz_job.entity.Address;
import com.orange.flavor_quartz_job.entity.ClientInfo;
import com.orange.flavor_quartz_job.model.request.ClientInfoRequest;
import com.orange.flavor_quartz_job.model.response.ClientInfoResponse;
import com.orange.flavor_quartz_job.repo.ClientInfoRepository;
import com.orange.flavor_quartz_job.scheduler.ClientInfoScheduler;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientInfoService {

    @Autowired
    private ClientInfoRepository clientInfoRepository;
    @Autowired
    private ClientInfoScheduler clientInfoScheduler;


    @Transactional
    public ClientInfoResponse addClientInfo(ClientInfoRequest clientInfoRequest) throws Exception {
        ClientInfo savedEntry = clientInfoRepository.save(buildClientInfo(clientInfoRequest));
        clientInfoScheduler.scheduleNewJob(savedEntry, clientInfoRequest);
        return buildClientInfoResponse(savedEntry);
    }

    public String deleteAll() throws Exception {
        clientInfoRepository.deleteAll();
        return "success";
    }

    private ClientInfoResponse buildClientInfoResponse(ClientInfo saveEntry) {
        ClientInfoResponse clientInfoResponse = new ClientInfoResponse();
        clientInfoResponse.setClientId(saveEntry.getClientId());
        clientInfoResponse.setJobName(saveEntry.getJobName());
        clientInfoResponse.setJobGroup(saveEntry.getJobGroup());
        clientInfoResponse.setName(saveEntry.getName());
        clientInfoResponse.setStatus("Saved");
        return clientInfoResponse;
    }

    private ClientInfo buildClientInfo(ClientInfoRequest clientInfoRequest) {
        Address address = new Address();
        address.setCity(clientInfoRequest.getAddress().getCity());
        address.setState(clientInfoRequest.getAddress().getState());
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setNoOfDaysAfterToReminder(clientInfo.getNoOfDaysAfterToReminder());
        clientInfo.setClientId(clientInfoRequest.getClientId());
        clientInfo.setJobName(clientInfoRequest.getJobName());
        clientInfo.setJobGroup(clientInfoRequest.getJobGroup());
        clientInfo.setName(clientInfoRequest.getName());
        clientInfo.setEmail(clientInfoRequest.getEmail());
        clientInfo.setPhone(clientInfoRequest.getPhone());
        clientInfo.setContactPerson(clientInfoRequest.getContactPerson());
        clientInfo.setBusinessType(clientInfoRequest.getBusinessType());
        clientInfo.setLastOrderDate(clientInfoRequest.getLastOrderDate());
        clientInfo.setPreferredChemicals(clientInfoRequest.getPreferredChemicals());
        clientInfo.setNotes(clientInfoRequest.getNotes());
        clientInfo.setAddress(address);
        return clientInfo;
    }

}
