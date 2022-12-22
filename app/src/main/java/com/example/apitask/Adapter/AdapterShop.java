package com.example.apitask.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apitask.Model.Shop;
import com.example.apitask.R;

import java.util.List;

public class AdapterShop extends BaseAdapter {

    private Context mContext;
    List<Shop> shopList;

    public AdapterShop(Context mContext, List<Shop> shopList) {
        this.mContext = mContext;
        this.shopList = shopList;
    }

    @Override
    public int getCount() {
        return shopList.size();
    }

    @Override
    public Object getItem(int i) {
        return shopList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return shopList.get(i).getID();
    }

    private Bitmap getImage(String encodedImg)
    {

        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else
        {
            return null;

        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(mContext, R.layout.item_layuot,null);
        TextView Title = v.findViewById(R.id.txtTitle);
        TextView Count = v.findViewById(R.id.articleNumber);
        TextView Price = v.findViewById(R.id.textPriceItog);
        ImageView imageView = v.findViewById(R.id.imageView);
        Button deleteShop = v.findViewById(R.id.deleteShop);

        deleteShop.setId(i);

        Shop shop = shopList.get(i);
        Title.setText(shop.getTitle());
        Count.setText(Integer.toString(shop.getCount()));
        Price.setText(Integer.toString((int) shop.getPrice()));
        return v;
    }
}
