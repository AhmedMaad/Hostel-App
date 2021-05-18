package com.example.hostel;


public class UserModel {

    //private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String gender;

    public UserModel(){

    }

    public UserModel(String name, String email, String phoneNumber, String gender) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    /*public String getId() {
        return id;
    }*/

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

}
