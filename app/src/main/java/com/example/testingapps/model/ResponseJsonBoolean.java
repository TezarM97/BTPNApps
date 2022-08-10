package com.example.testingapps.model;

import com.google.gson.annotations.SerializedName;

public class ResponseJsonBoolean {
    @SerializedName("response")
    private com.example.testingapps.model.ResponseModel response;
    @SerializedName("data")
    private boolean data;

    public com.example.testingapps.model.ResponseModel getResponse() {
        return response;
    }

    public boolean getData() {
        return data;
    }
}
