package com.example.hostel;

public class ProductModel {

    private String id;
    private String picture;
    private String address;
    private double price;
    private String specifications;
    private String name;
    private String phone;
    private String productType;
    private String userId;

    //Required Empty Constructor for reading from firebase
    public ProductModel(){}

    public ProductModel(String picture, String address, double price
            , String specifications, String name, String phone
            , String productType, String userId) {
        this.picture = picture;
        this.address = address;
        this.price = price;
        this.specifications = specifications;
        this.name = name;
        this.phone = phone;
        this.productType = productType;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getPicture() {
        return picture;
    }

    public String getAddress() {
        return address;
    }

    public double getPrice() {
        return price;
    }

    public String getSpecifications() {
        return specifications;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getProductType() {
        return productType;
    }

    public String getUserId() {
        return userId;
    }

    public void setId(String id){
        this.id = id;
    }

}
