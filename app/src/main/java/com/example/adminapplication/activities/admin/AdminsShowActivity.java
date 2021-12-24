package com.example.adminapplication.activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.adminapplication.R;
import com.example.adminapplication.api.APIService;
import com.example.adminapplication.network.RetrofitClient;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class AdminsShowActivity extends AppCompatActivity {
    private ImageView imageView;
    private ProgressBar progressBar;
    private String name,image,date,balance;
    private APIService apiService;

    private TextView showName,showDate,showBalance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admins_show);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageView=findViewById(R.id.show_image);
        showName=findViewById(R.id.showName);
        showDate=findViewById(R.id.showDate);
        showBalance=findViewById(R.id.showBalance);
        progressBar =findViewById(R.id.showAdminProgress);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        image = intent.getStringExtra("image");
        date = intent.getStringExtra("date");
        balance = intent.getStringExtra("balance");
        apiService= RetrofitClient.getInstance().getApiService();
        showName.setText(name);
        showDate.setText(date);
        Log.d("Adaptor data","balance "+balance);

        showBalance.setText(balance);

Log.e("image","https://onlinesd.store/billing/ImageUploadApi/uploads/improvement/"+image);
        Picasso.get().load("https://onlinesd.store/billing/ImageUploadApi/uploads/improvement/"+image).error(R.drawable.ic_error).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onError(Exception e) {
                progressBar.setVisibility(View.GONE);
                Log.d("Error : ", e.getMessage());
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}