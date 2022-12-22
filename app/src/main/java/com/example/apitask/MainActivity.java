package com.example.apitask;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.apitask.Adapter.AdapterShop;
import com.example.apitask.Model.Shop;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private AdapterShop adapterShop;
    private List<Shop> shopList = new ArrayList<>();

    EditText search;
    Button ButAdd;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ListView ListShop = findViewById(R.id.ListShop);
        adapterShop = new AdapterShop(MainActivity.this, shopList);
        ListShop.setAdapter(adapterShop);

        new GetShop().execute();

        ButAdd = findViewById(R.id.addNew);
        ButAdd.setOnClickListener(this);

        search = findViewById(R.id.Search);
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                new GetShop().execute();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                new GetShop().execute();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.addNew:
                startActivity(new Intent(this, AddActivity.class));
                finish();
                break;
        }
        }


    private class GetShop extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... voids)
        {
            try
            {
                URL url = new URL("https://ngknn.ru:5001/NGKNN/ГромовАН/Api/Shops");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null)
                {
                    result.append(line);
                }
                return result.toString();
            }
            catch (Exception exception)
            {
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            try
            {
                shopList.clear();
                JSONArray tempArray = new JSONArray(s);
                for(int i = 0; i < tempArray.length(); i++)
                {
                    JSONObject appJson = tempArray.getJSONObject(i);
                    Shop tempApp = new Shop(
                            appJson.getInt("ID"),
                            appJson.getString("Title"),
                            appJson.getDouble("Price"),
                            appJson.getInt("Count"),
                            appJson.getString("Image")
                    );
                    shopList.add(tempApp);
                    adapterShop.notifyDataSetInvalidated();
                }
            }
            catch (Exception ignored)
            {

            }
        }
    }

}