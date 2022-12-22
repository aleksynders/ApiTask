package com.example.apitask;

public class DataModal {

    private int ID;
    private String Title;
    private int Price;
    private int Count;
    private String Image;

    public DataModal(String title, int price, int count, String image) {
        Title = title;
        Price = price;
        Count = count;
        Image = image;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
