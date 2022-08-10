package com.example.testingapps.model;

import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.SerializedName;

public class ResponseJsonPrimitive {
    @SerializedName("response")
    private com.example.testingapps.model.ResponseModel response;
    @SerializedName("data")
    private JsonPrimitive data;

    public com.example.testingapps.model.ResponseModel getResponse() {
        return response;
    }

    public JsonPrimitive getData() {
        return data;
    }
}
