package com.example.adminapplication.activities.users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.adminapplication.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class FullShowActivity extends AppCompatActivity {
private ImageView imageView;
private String image;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_show);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        imageView = findViewById(R.id.full_image);
        progressBar = findViewById(R.id.fullProgress);
        Intent intent = getIntent();
        image = intent.getStringExtra("image");
        Log.e("image", "https://onlinesd.store/billing/ImageUploadApi/uploads/improvement/" + image);
        Picasso.get().load("https://onlinesd.store/billing/ImageUploadApi/uploads/improvement/" + image).error(R.drawable.ic_error).into(imageView, new Callback() {

            // Picasso.get().load("http://10.0.2.2/photos/"+image).error(R.drawable.ic_error).into(imageView, new Callback() {
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;}
        return super.onOptionsItemSelected(item);

    }
}