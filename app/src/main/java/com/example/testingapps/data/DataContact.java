package com.example.testingapps.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataContact {
    @Expose
    @SerializedName("id")
    private String IdUser;

    @Expose
    @SerializedName("firstName")
    private String FirstName;

    @Expose
    @SerializedName("lastName")
    private String LastName;

    @Expose
    @SerializedName("photo")
    private String Photo;


    @Expose
    @SerializedName("age")
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }
}
