package com.example.apnabazaar;

class products {
    private String Name,Description,Price,image;

    public products(){

    }

    public products(String name, String description, String price, String image) {
        Name = name;
        Description = description;
        Price = price;
        this.image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
