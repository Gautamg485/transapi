package com.happy.transapi.repositories;

import com.happy.transapi.entities.TransMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransMasterRepository extends JpaRepository<TransMaster, Long> {
    @Query("SELECT t FROM TransMaster t where t.name=:name")
    TransMaster findValueByName(@Param("name") String name);
}

