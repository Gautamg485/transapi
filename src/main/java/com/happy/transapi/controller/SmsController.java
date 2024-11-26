package com.happy.transapi.controller;

import com.google.gson.Gson;
import com.happy.transapi.entities.SmsDataLogs;
import com.happy.transapi.exceptions.InvalidRequestException;
import com.happy.transapi.reponses.GenericResponse;
import com.happy.transapi.reponses.SmsDataResponse;
import com.happy.transapi.repositories.SmsDataLogsRepository;
import com.happy.transapi.requests.transaction.GetSmsDataRequest;
import com.happy.transapi.requests.transaction.SaveSmsDataRequest;
import com.happy.transapi.utils.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class SmsController {

    @Autowired
    SmsDataLogsRepository smsDataLogsRepository;
    @Autowired
    GeneralUtil generalUtil;

    @PostMapping("/saveSmsData")
    public <T> ResponseEntity saveSmsData(@RequestBody @Valid SaveSmsDataRequest request, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        SmsDataLogs smsDataLogs = smsDataLogsRepository.findLogsByUserId(request.getUserId());

        if (smsDataLogs==null) {
            smsDataLogs = new SmsDataLogs();
            smsDataLogs.setUserId(request.getUserId());
        }
        smsDataLogs.setData(request.getTransactionData());

        smsDataLogsRepository.save(smsDataLogs);

        return new ResponseEntity(new GenericResponse<String>(200, "Successfully Updated"), null, HttpStatus.OK);
    }

    @PostMapping("/getSmsData")
    public <T> ResponseEntity getSmsData(@RequestBody @Valid GetSmsDataRequest request, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        SmsDataLogs smsDataLogs = smsDataLogsRepository.findLogsByUserId(request.getUserId());
        SmsDataResponse smsDataResponse = new SmsDataResponse();
        if (smsDataLogs!=null) {
            smsDataResponse.setSmsData(smsDataLogs.getData());
            smsDataResponse.setSmsLastUpdatedAt(smsDataLogs.getUpdatedAt());
        }
        smsDataResponse.setScamRegex(new Gson().fromJson(generalUtil.getMasterValue("scamRegex"), ArrayList.class));
        smsDataResponse.setBankNameRegex(new Gson().fromJson(generalUtil.getMasterValue("bankNameRegex"), ArrayList.class));
        smsDataResponse.setTransactionTypeRegex(new Gson().fromJson(generalUtil.getMasterValue("transactionTypeRegex"), ArrayList.class));
        smsDataResponse.setMerchantRegex(new Gson().fromJson(generalUtil.getMasterValue("merchantNameRegex"), ArrayList.class));

        return new ResponseEntity(new GenericResponse<SmsDataResponse>(200, smsDataResponse), null, HttpStatus.OK);
    }
}
