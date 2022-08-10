package com.example.testingapps.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataContactProfile {
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
    @SerializedName("age")
    private int age;

    @Expose
    @SerializedName("photo")
    private String Photo;

    public DataContactProfile(String idUser, String firstName, String lastName, int age, String photo) {
        IdUser = idUser;
        FirstName = firstName;
        LastName = lastName;
        this.age = age;
        Photo = photo;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }
}
