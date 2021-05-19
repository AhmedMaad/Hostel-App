package com.example.hostel;


public class UserModel {

    private String name;
    private String email;
    private String phoneNumber;
    private String gender;
    private String profilePicture;

    public UserModel(){

    }

    public UserModel(String name, String email, String phoneNumber, String gender) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public UserModel(String name, String email, String phoneNumber, String gender
            , String profilePicture) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.profilePicture = profilePicture;
    }

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

    public String getProfilePicture(){
        return profilePicture;
    }

}
