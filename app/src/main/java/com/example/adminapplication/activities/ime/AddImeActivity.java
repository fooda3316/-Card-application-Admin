package com.example.adminapplication.activities.ime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.adminapplication.R;
import com.example.adminapplication.api.APIService;
import com.example.adminapplication.model.Ime;
import com.example.adminapplication.model.Result;
import com.example.adminapplication.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddImeActivity extends AppCompatActivity {
    private Button button;
    private EditText edtIme,edtName;
    private APIService apiService;
    private MaterialDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ime);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        edtIme =findViewById(R.id.ime_val);
        button =findViewById(R.id.btnAddImeN);
        edtName =findViewById(R.id.ime_name);

        apiService= RetrofitClient.getInstance().getApiService();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtIme.getText().toString().isEmpty()){
                    edtIme.setError("لا يمكن ترك هذا الحقل فارغاً");
                    vibrate();
                }
              else  if (edtName.getText().toString().isEmpty()){
                    edtName.setError("لا يمكن ترك هذا الحقل فارغاً");
                    vibrate();
                }
                else {
                    String imeVal= edtIme.getText().toString().trim();
                    String imeName= edtName.getText().toString().trim();
                    addIme(new Ime(imeVal,imeName));
                }
                
            }
        });

    }
    private void addIme(Ime ime) {
        showLoadingDialog();
        apiService= RetrofitClient.getInstance().getApiService();
        // Log.e("card data","name  "+card.getTitle()+" SubName "+card.getSubName()+" Serialnumber "+card.getSerialnumber()+" value "+card.getValue());
        Call<Result> call=apiService.addIme(ime.getIme(),ime.getName());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (!response.isSuccessful()) {
                    Log.e("error code","Code: " + response.code());
                    Toast.makeText(AddImeActivity.this, "لم تتم إضافة الرقم بنجاح", Toast.LENGTH_LONG).show();
                    return;
                }
                assert response.body() != null;
                if(!response.body().getError()) {
                    dismissLoadingDialog();
                    Log.d("Message", response.body().getMessage());
                    Toast.makeText(AddImeActivity.this, "تم إضافة الرقم بنجاح", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    dismissLoadingDialog();
                    Toast.makeText(AddImeActivity.this, "لم تتم إضافة الرقم بنجاح", Toast.LENGTH_LONG).show();
                    vibrate();

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("Add admin card error",t.toString());
                dismissLoadingDialog();
                Toast.makeText(AddImeActivity.this, "لم تتم إضافة البطاقة بنجاح", Toast.LENGTH_LONG).show();
                vibrate();
            }
        });
    }

    public void showLoadingDialog() {
        mDialog = new MaterialDialog.Builder(AddImeActivity.this)
                .title(R.string.app_name)
                .content(R.string.progress_wait)
                .progress(true, 0)
                .show();
    }
    public void dismissLoadingDialog() {
        mDialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
    public void vibrate() {
        Vibrator vibs = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibs.vibrate(200);
    }
}