package com.example.testingapps.model;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

public class ResponseJsonString {
    @SerializedName("response")
    private com.example.testingapps.model.ResponseModel response;
    @SerializedName("message")
    private JsonElement data;

    public com.example.testingapps.model.ResponseModel getResponse() {
        return response;
    }

    public JsonElement getData() {
        return data;
    }
}
