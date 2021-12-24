package com.example.adminapplication.activities.users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.adminapplication.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import static com.example.adminapplication.utils.Constants.BALANCE_KEY;
import static com.example.adminapplication.utils.Constants.EMAIL_KEY;
import static com.example.adminapplication.utils.Constants.IMAGE_KEY;
import static com.example.adminapplication.utils.Constants.NAME_KEY;
import static com.example.adminapplication.utils.Constants.PASSWORD_KEY;

public class ShowUserActivity extends AppCompatActivity {
    private String name ,email,password,image,balance;
    private TextView showName,showPass,showEmail,showBalance;
    private ImageView imageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_show_user);
        showName=findViewById(R.id.showUserName);
        showEmail=findViewById(R.id.showUserEmail);
        showPass=findViewById(R.id.showUserPassword);
        showBalance=findViewById(R.id.showUserBalance);
        progressBar=findViewById(R.id.showUserProgress);
        imageView=findViewById(R.id.showUserImage);

        Intent intent = getIntent();
        name = intent.getStringExtra(NAME_KEY);
        email = intent.getStringExtra(EMAIL_KEY);
        password = intent.getStringExtra(PASSWORD_KEY);
        image = intent.getStringExtra(IMAGE_KEY);
        balance = intent.getIntExtra(BALANCE_KEY,-1)+"";
        showName.setText(name);
        showEmail.setText(email);
        showPass.setText(password);
        showBalance.setText(balance);
      //  Log.e("user data","name "+name+" email "+email+" password "+password+" balance "+balance+" image "+image);

        Picasso.get().load("https://onlinesd.store/billing/ImageUploadApi/uploads/registrations/"+image).error(R.drawable.ic_error).into(imageView, new Callback() {
     @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                Log.d("Error : ", e.getMessage());
                progressBar.setVisibility(View.GONE);

            }
        });


    }
    public static void actionStart(Context context, String name, String email, String password,String photo,String balance) {
        Intent intent = new Intent(context, ShowUserActivity.class);
        intent.putExtra(NAME_KEY,name);
        intent.putExtra(EMAIL_KEY,email);
        intent.putExtra(PASSWORD_KEY,password);
        intent.putExtra(IMAGE_KEY,photo);
        intent.putExtra(BALANCE_KEY,balance);
        context.startActivity(intent);
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