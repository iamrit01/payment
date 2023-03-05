package com.example.pradhuman.utils;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class BaseEntityResponse<T> {

    public static final int SUCCESS = 1;

    public static final int FAILED = 0;

    private String statusReason;

    @Builder.Default
    private int statusCode = FAILED;

    private T entity;

    private List<T> entities;

    public BaseEntityResponse(T entity) {
        super();
        this.entity = entity;
    }

    public BaseEntityResponse(List<T> entities){
        super();
        this.entities = entities;
    }

    public BaseEntityResponse(){

    }

    public static BaseEntityResponse getFailedResponse(String reason) {
        BaseEntityResponse response = new BaseEntityResponse();
        response.setStatusCode(BaseEntityResponse.FAILED);
        response.setStatusReason(reason);
        return response;
    }

    public static BaseEntityResponse getSuccessResponse(String reason) {
        BaseEntityResponse response = new BaseEntityResponse();
        response.setStatusCode(BaseEntityResponse.SUCCESS);
        response.setStatusReason(reason);
        return response;
    }

}
