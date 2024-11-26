package com.happy.transapi.utils;

import com.happy.transapi.entities.TransMaster;
import com.happy.transapi.repositories.TransMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GeneralUtil {

    @Autowired
    TransMasterRepository transMasterRepository;

    public String getMasterValue(String name) {
        TransMaster transMaster = transMasterRepository.findValueByName(name);

        if (transMaster!=null) {
            return transMaster.getValue();
        }

        return "";
    }
}
