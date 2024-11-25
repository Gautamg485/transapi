package com.happy.transapi.reponses;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ValidationResponse {
    private String field;
    private String error;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field=field;
    }

    public Object getError() {
        return error;
    }

    public void setError(String error) {
        this.error=error;
    }

    public List getErrorResponse(BindingResult bindingResult) {
        List<ValidationResponse> errorList = new ArrayList<ValidationResponse>();
        for (Object object : bindingResult.getAllErrors()) {
            if(object instanceof FieldError) {
                ValidationResponse validResponse = new ValidationResponse();
                FieldError fieldError = (FieldError) object;
                validResponse.setField(fieldError.getField());
                validResponse.setError(fieldError.getDefaultMessage());
                errorList.add(validResponse);
            }
        }

        return errorList;
    }
}
