package com.example.adminapplication.activities.cards;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.adminapplication.R;
import com.example.adminapplication.activities.admin.AllCardsAddminActivity;
import com.example.adminapplication.api.APIService;
import com.example.adminapplication.model.AdminCard;
import com.example.adminapplication.model.Card;
import com.example.adminapplication.model.Result;
import com.example.adminapplication.model.Results;
import com.example.adminapplication.network.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.adminapplication.activities.admin.AllCardsAddminActivity.ERROR_MESSAGE;
import static com.example.adminapplication.utils.Constants.CARD_BRANCH;
import static com.example.adminapplication.utils.Constants.CARD_COUNT;
import static com.example.adminapplication.utils.Constants.CARD_ID;
import static com.example.adminapplication.utils.Constants.CARD_TITLE;
import static com.example.adminapplication.utils.Constants.CARD_TYPE;

public class SubCardsShowActivity extends AppCompatActivity {
    private TextView textView,cardsInfo;
    private EditText editText;
    private int cardID,cardCount=0;
    private String cardTitle,cardType,cardBranch,serialNumber,cardsCount;
    private APIService apiService;
    private MaterialDialog mDialog;
    Call<Results> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_cards_show);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        editText=findViewById(R.id.edtCardShow);
        textView=findViewById(R.id.sub_cards_show_count);
        cardsInfo=findViewById(R.id.sub_cards_show_info);

        Intent intent = getIntent();
        cardID=intent.getIntExtra(CARD_ID,0);
        cardTitle=intent.getStringExtra(CARD_TITLE);
        cardType=intent.getStringExtra(CARD_TYPE);
        cardBranch=intent.getStringExtra(CARD_BRANCH);
        cardCount=intent.getIntExtra(CARD_COUNT,0);
        cardsInfo.setText(" Card info: "+cardType+" "+cardBranch+" "+cardTitle);

        apiService= RetrofitClient.getInstance().getApiService();
        call=apiService.getAllAdminCardsNew(cardType,cardBranch,cardTitle);
        showLoadingDialog();
        call.enqueue(new Callback<Results>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                if (!response.isSuccessful()) {
                    Log.e("error code", "Code: " + response.code());
                    return;
                }
               // assert response.body() != null;
                ArrayList<AdminCard> cards=new ArrayList<>();
                cards.addAll(response.body().getAdminCards());
                //cardCount=cards.size();
                for (AdminCard adminCard:cards) {
                   // cardCount++;
                   Log.e("info",adminCard.getSerialnumber()) ;
                }
                if (cardCount>0){
                    textView.setText(" عدد البطاقات المتوفرة هو "+cardCount);
                }
                else {
                    textView.setText("عفوا لا تتوفر بطاقات من هذا النوع ");

                }

                dismissLoadingDialog();
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.e("onFailure",t.toString());
                dismissLoadingDialog();
            }
        });



        findViewById(R.id.addCardsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCardData();
                if (checkIfIsTextEmpty()){
                    addNewCard(new AdminCard(cardTitle,cardType,cardBranch,serialNumber));

                }
            }
        });
    }

    private void getCardData() {
        serialNumber=editText.getText().toString().trim();
        //value= Integer.parseInt(edtValue.getText().toString().trim());
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
    private void addNewCard(AdminCard card) {
        showLoadingDialog();

        // Log.e("card data","name  "+card.getTitle()+" SubName "+card.getSubName()+" Serialnumber "+card.getSerialnumber()+" value "+card.getValue());
        Call<Result> call=apiService.addAdminCards(card.getTitle(),card.getSubName(),card.getBranch(),card.getSerialnumber());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (!response.isSuccessful()) {
                    Log.e("error code","Code: " + response.code());
                    makeToast("لم تتم إضافة البطاقة بنجاح");

                    return;
                }
                assert response.body() != null;
                if(!response.body().getError()) {
                    dismissLoadingDialog();
                    Log.d("Message", response.body().getMessage());
                    makeToast("تم إضافة البطاقة بنجاح");

                    finish();
                }
                else {
                    dismissLoadingDialog();
              makeToast("لم تتم إضافة البطاقة بنجاح");
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("Add admin card error",t.toString());
                dismissLoadingDialog();
                makeToast("لم تتم إضافة البطاقة بنجاح");


            }
        });
    }

    private void makeToast(String message) {
        Toast.makeText(SubCardsShowActivity.this, message, Toast.LENGTH_LONG).show();

    }

    private Boolean checkIfIsTextEmpty() {

        if (editText.getText().toString().trim().isEmpty()) {
            editText.setError(ERROR_MESSAGE);
            return false;
        }
//        if (edtValue.getText().toString().trim().isEmpty()) {
//            edtValue.setError(ERROR_MESSAGE);
//            return false;
//        }
        return true;
      }
    public void showLoadingDialog() {
        mDialog = new MaterialDialog.Builder(SubCardsShowActivity.this)
                .title(R.string.app_name)
                .content(R.string.progress_wait)
                .progress(true, 0)
                .show();
    }
    public void dismissLoadingDialog() {
        mDialog.dismiss();
    }
    }
