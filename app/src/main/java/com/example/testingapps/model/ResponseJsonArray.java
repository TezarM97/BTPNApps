package com.example.testingapps.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class ResponseJsonArray {
    @SerializedName("response")
    private com.example.testingapps.model.ResponseModel response;
    @SerializedName("data")
    private JsonArray data;

    @SerializedName("message")
    private JsonElement message;

    public JsonElement getMessage() {
        return message;
    }
    //
//    public JsonArray getMessage() {
//        return message;
//    }

    public com.example.testingapps.model.ResponseModel getResponse() {
        return response;
    }

    public JsonArray getData() {
        return data;
    }
}
