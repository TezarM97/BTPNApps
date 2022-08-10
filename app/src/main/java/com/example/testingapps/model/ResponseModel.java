package com.example.testingapps.model;

import com.google.gson.annotations.SerializedName;

public class ResponseModel {
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;


    @SerializedName("code")
    private int Code;

    public int getCode() {
        return Code;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
