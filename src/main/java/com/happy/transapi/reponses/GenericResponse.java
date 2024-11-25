package com.happy.transapi.reponses;
public class GenericResponse<T> {
    private Integer statusCode;
    private T data;

    public GenericResponse(Integer statusCode, T resultData) {
        this.statusCode=statusCode;
        this.data=(T) resultData;
    }

    public T getData() {
        return data;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}

