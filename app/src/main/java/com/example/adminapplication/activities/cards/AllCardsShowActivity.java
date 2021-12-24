package com.example.adminapplication.activities.cards;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.adminapplication.R;
import com.example.adminapplication.api.APIService;
import com.example.adminapplication.dialog.CustomDialogListener;
import com.example.adminapplication.dialog.EditDialogFragment;
import com.example.adminapplication.model.Result;
import com.example.adminapplication.network.RetrofitClient;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Response;

public class AllCardsShowActivity extends AppCompatActivity implements CustomDialogListener {
    private String name,subName,branch,image,cardTitle;
    private int balance;
    private ImageView imageView;
    private ProgressBar progressBar;
    private TextView showName,showBalance;
    private Button button;
    private APIService apiService;
    private LinearLayout linearLayout;
    private  TextView editResultShow;
    private String imageName;
    private MaterialDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allcards_show);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        subName = intent.getStringExtra("sub name");
        branch = intent.getStringExtra("branch");
        image = intent.getStringExtra("image");
        balance = intent.getIntExtra("balance",-1);
        Log.e("Activity data"," name "
                +name+"balance "+balance);

        // id = intent.getIntExtra("id",-1);
        imageView=findViewById(R.id.show_image);
        showName=findViewById(R.id.showName);
        showBalance=findViewById(R.id.showBalance);
        progressBar =findViewById(R.id.showProgress);
        button=findViewById(R.id.editCardAdminButton);
        linearLayout=findViewById(R.id.cardsShowLayout);
        editResultShow =findViewById(R.id.editResultShowTv);
        apiService = RetrofitClient.getInstance().getApiService();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (button.getText().equals("رجوع")){
                   finish();
               }
               showEditDialog();
            }
        });
        String cardName=subName+" "+getCardTitle(name)+" "+getCardBranch(branch);
        showName.setText(cardName);
        Log.d("show data","cardName "+cardName);

        showBalance.setText(balance+"");
        //showName.setText(name);

        Log.e("all Cards Show Activity","https://onlinesd.store/billing/images/"+image);
        Picasso.get().load("https://onlinesd.store/billing/ImageUploadApi/uploads/cards/"+image).error(R.drawable.ic_error).into(imageView, new Callback() {

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
    public String getDeviceIMEI() {
        String deviceUniqueIdentifier = null;
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != tm) {
            deviceUniqueIdentifier = tm.getDeviceId();
        }
        if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
            deviceUniqueIdentifier = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceUniqueIdentifier;
    }
    private void editCard(String image,int value) {
        showLoadingDialog();
      //  Log.d("value : ", "value is: "+value);
        Log.d("image : ", "value is: "+image);
        apiService.updateCardValue(image,getDeviceIMEI(),value).enqueue(new retrofit2.Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                if (!response.isSuccessful()) {
                    Log.e("error code","Code: " + response.code());
                    Toast.makeText(AllCardsShowActivity.this, "لم يتم تعديل البطاقة بنجاح", Toast.LENGTH_LONG).show();
                    showEditResult("لم يتم تعديل البطاقة بنجاح");
                    return;
                }
                assert response.body() != null;
                if (!response.body().getError()){
                    Toast.makeText(AllCardsShowActivity.this, "تم تعديل البطاقة بنجاح", Toast.LENGTH_LONG).show();
                    showEditResult("تم تعديل البطاقة بنجاح");
                    button.setText("رجوع");
                    dismissLoadingDialog();
                }
                else {
                    Toast.makeText(AllCardsShowActivity.this, "لم يتم تعديل البطاقة بنجاح", Toast.LENGTH_LONG).show();
                    showEditResult("لم يتم تعديل البطاقة بنجاح");
                    dismissLoadingDialog();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("getVerificatin error",t.toString());
                Toast.makeText(AllCardsShowActivity.this, "لم يتم تعديل البطاقة بنجاح", Toast.LENGTH_LONG).show();
                showEditResult("لم يتم تعديل البطاقة بنجاح");
                dismissLoadingDialog();

            }
        });
    }



    @Override
    public void onDialogPositive(String pass) {

    }
    @Override
    public void editValue(String value) {
        long longVal= Long.parseLong(value);
        editCard(image,(int)longVal);
        Log.e("image",image);

    }

    @Override
    public void nameValue(String name) {

    }

    private void showEditDialog() {
        EditDialogFragment dialog = new EditDialogFragment();
        dialog.show(getSupportFragmentManager(), "EditDialogFragment");

    }
    private void showEditResult(String message){
     linearLayout.setVisibility(View.GONE);
        editResultShow.setVisibility(View.VISIBLE);
        editResultShow.setText(message);
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
    public void showLoadingDialog() {
        mDialog = new MaterialDialog.Builder(AllCardsShowActivity.this)
                .title(R.string.app_name)
                .content(R.string.progress_wait)
                .progress(true, 0)
                .show();
    }
    public void dismissLoadingDialog() {
        mDialog.dismiss();
    }
    private String getCardBranch(String branch){

        String card_branch="";
        if (branch.equals("a")){
            card_branch="";
        }
        else {
            card_branch=branch;
        }
        return card_branch;

    }

    private String getCardTitle(String title){

        if (!title.contains("Months")){
            cardTitle=title+" $";
        }
        else {
            cardTitle=title;
        }
        return cardTitle;

    }

}