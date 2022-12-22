package com.example.apitask.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Shop implements Parcelable{
    private int ID;
    private String Title;
    private double Price;
    private int Count;
    private String Image;

    public Shop(int ID, String title, double price, int count, String image) {
        this.ID = ID;
        this.Title = title;
        this.Price = price;
        this.Count = count;
        this.Image = image;
    }

    protected Shop(Parcel in)
    {
        ID = in.readInt();
        Title = in.readString();
        Price = in.readFloat();
        Count = in.readInt();
        Image = in.readString();
    }


    public static final Creator<Shop> CREATOR = new Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel in) {
            return new Shop(in);
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ID);
        parcel.writeString(Title);
        parcel.writeDouble(Price);
        parcel.writeInt(Count);
        parcel.writeString(Image);
    }

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return Title;
    }

    public double getPrice() {
        return Price;
    }

    public int getCount() {
        return Count;
    }

    public String getImage() {
        return Image;
    }
}
