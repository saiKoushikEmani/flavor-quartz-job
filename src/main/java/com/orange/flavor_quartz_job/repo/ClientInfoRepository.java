package com.orange.flavor_quartz_job.repo;

import com.orange.flavor_quartz_job.entity.ClientInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientInfoRepository extends JpaRepository<ClientInfo, Long> {
    ClientInfo findByJobName(String jobName);

    Optional<ClientInfo> findByJobNameAndClientId(String jobName, String clientId);


    @Modifying
    @Transactional
    @Query("""
             UPDATE ClientInfo c
                SET c.jobStatus = :jobStatus,
                    c.repeatCount = :repeatCount
                WHERE c.clientId = :clientId
                  AND c.jobName = :jobName
            """)
    int updateJobStatusAndRepeatCountByClientIdAndJobName(@Param("jobStatus") String jobStatus,
                                                          @Param("repeatCount") int repeatCount,
                                                          @Param("clientId") String clientId,
                                                          @Param("jobName") String jobName);

}