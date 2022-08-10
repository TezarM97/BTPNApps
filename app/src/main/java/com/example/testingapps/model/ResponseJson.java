package com.example.testingapps.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class ResponseJson {
    @SerializedName("response")
    private com.example.testingapps.model.ResponseModel response;
    @SerializedName("message")
    private JsonObject data;

    public com.example.testingapps.model.ResponseModel getResponse() {
        return response;
    }

    public JsonObject getData() {
        return data;
    }
}
