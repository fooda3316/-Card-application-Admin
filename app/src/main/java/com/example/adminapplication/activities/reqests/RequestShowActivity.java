package com.example.adminapplication.activities.reqests;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.adminapplication.R;
import com.example.adminapplication.api.APIService;
import com.example.adminapplication.model.AdminCard;
import com.example.adminapplication.model.Result;
import com.example.adminapplication.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.adminapplication.activities.admin.AllCardsAddminActivity.ERROR_MESSAGE;

public class RequestShowActivity extends AppCompatActivity {
    private String cardType,cardTitle,cardBranch;
    private TextView type,title,branch;
    private Button button;
    private EditText edtNumber;
    private String serialNumber;
    private MaterialDialog mDialog;
    private APIService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_show);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        type=findViewById(R.id.card_type);
        branch=findViewById(R.id.cardBranch);
        title=findViewById(R.id.cardCategory);
        button=findViewById(R.id.addCardButton);
        edtNumber =findViewById(R.id.cardSerialNumberInput);
        //edtValue =findViewById(R.id.cardValueInput);
        apiService= RetrofitClient.getInstance().getApiService();
        Intent intent = getIntent();
        cardType = intent.getStringExtra("SubName");
        cardTitle = intent.getStringExtra("Title");
        cardBranch = intent.getStringExtra("Branch");
        type.setText(cardType);
        title.setText(cardTitle);
        branch.setText(cardBranch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCardData();
                if (checkIfIsTextEmpty()) {
                    addNewCard(new AdminCard(cardTitle,cardType,cardBranch,serialNumber));
                }
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
    private void getCardData() {
        serialNumber=edtNumber.getText().toString().trim();
        //value= Integer.parseInt(edtValue.getText().toString().trim());
    }
    private Boolean checkIfIsTextEmpty() {

        if (edtNumber.getText().toString().trim().isEmpty()) {
            edtNumber.setError(ERROR_MESSAGE);
            return false;
        }
//        if (edtValue.getText().toString().trim().isEmpty()) {
//            edtValue.setError(ERROR_MESSAGE);
//            return false;
//        }
        return true;
    }
    private void addNewCard(AdminCard card) {
        showLoadingDialog();
        apiService= RetrofitClient.getInstance().getApiService();
       // Log.e("card data","name  "+card.getTitle()+" SubName "+card.getSubName()+" Serialnumber "+card.getSerialnumber()+" value "+card.getValue());
        Call<Result> call=apiService.addAdminCards(card.getTitle(),card.getSubName(),card.getBranch(),card.getSerialnumber());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (!response.isSuccessful()) {
                    Log.e("error code","Code: " + response.code());
                    Toast.makeText(RequestShowActivity.this, "لم تتم إضافة البطاقة بنجاح", Toast.LENGTH_LONG).show();
                    return;
                }
                assert response.body() != null;
                if(!response.body().getError()) {
                    dismissLoadingDialog();
                    Log.d("Message", response.body().getMessage());
                    Toast.makeText(RequestShowActivity.this, "تم إضافة البطاقة بنجاح", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    dismissLoadingDialog();
                    Toast.makeText(RequestShowActivity.this, "لم تتم إضافة البطاقة بنجاح", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("Add admin card error",t.toString());
                dismissLoadingDialog();
                Toast.makeText(RequestShowActivity.this, "لم تتم إضافة البطاقة بنجاح", Toast.LENGTH_LONG).show();

            }
        });
    }
    public void showLoadingDialog() {
        mDialog = new MaterialDialog.Builder(RequestShowActivity.this)
                .title(R.string.app_name)
                .content(R.string.progress_wait)
                .progress(true, 0)
                .show();
    }
    public void dismissLoadingDialog() {
        mDialog.dismiss();
    }
}