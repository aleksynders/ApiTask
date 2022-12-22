package com.example.apitask;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    Button addNewZ, GoBack;
    EditText Title, Count, Price;

    String encodedImage;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Title = findViewById(R.id.titleET);
        Count = findViewById(R.id.countET);
        Price = findViewById(R.id.priceET);

        GoBack = findViewById(R.id.addBack);
        GoBack.setOnClickListener(this);

        addNewZ = findViewById(R.id.addBTN);
        addNewZ.setOnClickListener(this);
    }


    private String encodeImage(Bitmap bitmap) {
        int prevW = 150;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(bytes);
        }
        return "";
    }

    private final ActivityResultLauncher<Intent> pickImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                Uri uri = result.getData().getData();
                try {
                    InputStream is = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    image.setImageBitmap(bitmap);
                    encodedImage = encodeImage(bitmap);
                } catch (Exception e) {

                }
            }
        }
    });

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addBack:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.addBTN:
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://ngknn.ru:5001/NGKNN/ГромовАН/Api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
                DataModal modal = new DataModal(Title.getText().toString(), Integer.parseInt(Price.getText().toString()), Integer.parseInt(Count.getText().toString()), encodedImage);
                Call<DataModal> call = retrofitAPI.createPost(modal);
                call.enqueue(new Callback<DataModal>() {
                    @Override
                    public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                        if(!response.isSuccessful())
                        {
                            Toast.makeText(AddActivity.this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(AddActivity.this, "Данные добавлены", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddActivity.this, MainActivity.class));
                        finish();
                    }
                    @Override
                    public void onFailure(Call<DataModal> call, Throwable t) {
                        Toast.makeText(AddActivity.this, "Произошла ошибка: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }
    }
}