package com.happy.transapi.repositories;

import com.happy.transapi.entities.SmsDataLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsDataLogsRepository extends JpaRepository<SmsDataLogs, Long> {
    @Query("SELECT t FROM SmsDataLogs t where t.userId=:userId")
    SmsDataLogs findLogsByUserId(@Param("userId") Long userId);
}

