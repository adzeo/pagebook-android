package com.example.pagebook.responsemodel;

import com.google.gson.annotations.SerializedName;

public class GenericResponse {

    @SerializedName("status")
    private boolean status;

    @SerializedName("errorMessage")
    private String errorMessage;

    @SerializedName("body")
    private Object body;

    public GenericResponse() {
        this.errorMessage = "";
        this.body = null;
    }

    public GenericResponse(boolean status, String errorMessage, Object body) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.body = body;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}