package com.example.adminapplication.activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.adminapplication.R;
import com.example.adminapplication.api.APIService;
import com.example.adminapplication.model.AdminCard;
import com.example.adminapplication.model.Result;
import com.example.adminapplication.network.RetrofitClient;
import com.example.adminapplication.utils.Data;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCardsAddminActivity extends AppCompatActivity {

    private APIService apiService;
    private Button button;
    private EditText edtNumber,edtValue;
    private NiceSpinner type,branch,category;
    private String cardType,cardBranch,cardTitle;
    private String serialNumber;
    public static final String ERROR_MESSAGE="لا يمكن ترك هذا الحقل فارغاً";
    private Data data;
    private MaterialDialog mDialog;
    private List<String>categories=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cards_addmin);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        apiService= RetrofitClient.getInstance().getApiService();
        button=findViewById(R.id.addCardAdminButton);
        edtNumber =findViewById(R.id.cardSerialNumberInput);
        edtValue =findViewById(R.id.cardValueInput);
        type=findViewById(R.id.spinner_type);
        branch=findViewById(R.id.spinner_branch);
        category=findViewById(R.id.spinner_category);
        data=new Data();
        type.attachDataSource(data.getHomeList());
        branch.attachDataSource(data.getBranch());
        type.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                cardType = parent.getItemAtPosition(position).toString();
                Log.e("cardType",cardType);
            }
        });
        branch.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                cardBranch = parent.getItemAtPosition(position).toString();

            }
        });

      category.attachDataSource(data.getTitles());
        category.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                cardTitle = parent.getItemAtPosition(position).toString();

            }
        });
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

    private void getCardData() {
        serialNumber=edtNumber.getText().toString().trim();
        //value= Integer.parseInt(edtValue.getText().toString().trim());
    }

    private void addNewCard(AdminCard card) {
        showLoadingDialog();
        apiService= RetrofitClient.getInstance().getApiService();
       // Log.e("card data","name  "+card.getTitle()+" SubName "+card.getSubName()+" Serialnumber "+card.getSerialnumber()+" value "+card.getValue());
        Call<Result>call=apiService.addAdminCards(card.getTitle(),card.getSubName(),card.getBranch(),card.getSerialnumber());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (!response.isSuccessful()) {
                    Log.e("error code","Code: " + response.code());
                    Toast.makeText(AllCardsAddminActivity.this, "لم تتم إضافة البطاقة بنجاح", Toast.LENGTH_LONG).show();
                    return;
                }
                assert response.body() != null;
                if(!response.body().getError()) {
                    dismissLoadingDialog();
                    Log.d("Message", response.body().getMessage());
                    Toast.makeText(AllCardsAddminActivity.this, "تم إضافة البطاقة بنجاح", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    dismissLoadingDialog();
                    Toast.makeText(AllCardsAddminActivity.this, "لم تتم إضافة البطاقة بنجاح", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
            Log.e("Add admin card error",t.toString());
            dismissLoadingDialog();
                Toast.makeText(AllCardsAddminActivity.this, "لم تتم إضافة البطاقة بنجاح", Toast.LENGTH_LONG).show();

            }
        });
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
        mDialog = new MaterialDialog.Builder(AllCardsAddminActivity.this)
                .title(R.string.app_name)
                .content(R.string.progress_wait)
                .progress(true, 0)
                .show();
    }
    public void dismissLoadingDialog() {
        mDialog.dismiss();
    }
}