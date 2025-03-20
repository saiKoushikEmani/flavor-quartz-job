package com.orange.flavor_quartz_job.repo;

import com.orange.flavor_quartz_job.entity.ClientInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientInfoRepository extends JpaRepository<ClientInfo, Long> {
    ClientInfo findByJobName(String jobName);
}