package com.example.testingapps.network;

import com.example.testingapps.data.DataContactProfile;
import com.example.testingapps.model.ResponseJson;
import com.example.testingapps.model.ResponseJsonArray;
import com.example.testingapps.model.ResponseJsonString;
import com.google.gson.JsonElement;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("contact")
    Call<ResponseJsonArray> getDataAll(
    );

    @DELETE("contact/{id}")
    Call<ResponseJson> deleteData(
            @Path("id") String IdAkun
    );


    @Multipart
    @POST("contact")
    Call<DataContactProfile> postDataBaru(
            @Part("firstName") RequestBody firstName,
            @Part("lastName") RequestBody lastName,
            @Part("age") RequestBody age,
            @Part ("photo") RequestBody photo
    );

    @Multipart
    @PUT("contact/{id}")
    Call<DataContactProfile> updateData(
            @Path("id") String IdAkun,
            @Part("firstName") RequestBody firstName,
            @Part("lastName") RequestBody lastName,
            @Part("age") RequestBody age,
            @Part ("photo") RequestBody photo
    );
}
