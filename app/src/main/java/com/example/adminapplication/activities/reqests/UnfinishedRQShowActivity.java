package com.example.adminapplication.activities.reqests;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.afollestad.materialdialogs.MaterialDialog;
import com.example.adminapplication.R;
import com.example.adminapplication.api.APIService;
import com.example.adminapplication.dialog.CustomDialogFragment;
import com.example.adminapplication.dialog.CustomDialogListener;
import com.example.adminapplication.model.Result;
import com.example.adminapplication.network.RetrofitClient;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class UnfinishedRQShowActivity extends AppCompatActivity implements CustomDialogListener {

    private int requestID,id;
    private String name,image;
    private EditText editText,userName,password,rePass,verificationNu;
    private ImageView imageView;
    private Button button,logIn;
    private ProgressBar progressBar;
    private APIService apiService;
    private int sendingBalance;
    private TextView textView;
    private LinearLayout loginChargingLayout,chargingLayout;
    public static final String  IS_USER_REGISTERED="IS_USER_REGISTERED";
    public static final String USER_REGISTERED_NAME="USER_REGISTERED_NAME";
    private static final String USER_REGISTERED_PASS="USER_REGISTERED_PASS";
    private static final String ERROR_MESSAGE="لا يمكن ترك هذا الحقل فارغاً";
    private String uName,uPass,rePassw,outhCode,verificationPass;
    private CustomDialogFragment dialogFragment;
    private MaterialDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unfinished_r_q_show);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editText=findViewById(R.id.unfinishedRQInput);
        imageView=findViewById(R.id.unfinishedRQImage);
        button=findViewById(R.id.unfinishedRQButton);
        textView=findViewById(R.id.unfinishedRQShow);
       progressBar =findViewById(R.id.unfinishedRQpro);
        chargingLayout=findViewById(R.id.chargingLayout);
        loginChargingLayout=findViewById(R.id.loginChargingLayout);
        userName=findViewById(R.id.unfinishedRQInputName);
        password=findViewById(R.id.unfinishedRQInputPass);
        rePass=findViewById(R.id.unfinishedRQInputRePass);
        verificationNu=findViewById(R.id.unfinishedRQInputVE);
        logIn=findViewById(R.id.unfinishedRQButtonLogin);
        Log.e("ime",getDeviceIMEI());
        apiService= RetrofitClient.getInstance().getApiService();

        //getInputs();
        Intent intent = getIntent();
        if (isLogIn()){
            loginChargingLayout.setVisibility(View.GONE);
            chargingLayout.setVisibility(View.VISIBLE);

        }
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            logInNow();
            }
        });
        requestID = intent.getIntExtra("id",-1);
        id = intent.getIntExtra("userID",-1);
        name = intent.getStringExtra("name");
        image = intent.getStringExtra("image");
if (editText.getText()!=null)  {
}
        Log.e("image","https://onlinesd.store/billing/ImageUploadApi/uploads/improvement/"+image);
        Picasso.get().load("https://onlinesd.store/billing/ImageUploadApi/uploads/improvement/"+image).error(R.drawable.ic_error).into(imageView, new Callback() {

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
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UnfinishedRQShowActivity.this,FullShowActivity.class).putExtra("image",image));
            }
        });
        //chargeNow(sendingBalance,id,requestID);
      button.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           if (editText.getText().toString().isEmpty()) {
               editText.setError("لا يمكن ترك هذا الحقل فارغاً");
           } else {
               sendingBalance = Integer.parseInt(editText.getText().toString().trim());
               //  Log.d("data","sendingBalance is "+sendingBalance);
               //     Log.d("data","balance "+sendingBalance+" id "+id+" requestID "+requestID);


               if (button.getText() == "رجوع") {
                   finish();
                   startActivity(new Intent(UnfinishedRQShowActivity.this, UnfinishedRQActivity.class));
               } else {
                   //chargeNow(sendingBalance,id,requestID);
                   showCustomDialog();
               }
           }
       }
});

    }

    private void logInNow() {
        uName=userName.getText().toString().trim();
        uPass=password.getText().toString().trim();
        rePassw=rePass.getText().toString().trim();
        outhCode=verificationNu.getText().toString().trim();
        checkIfIsTextEmpty();
        if ((userName.getText().toString().isEmpty())&&
                (password.getText().toString().isEmpty())&&
                (rePass.getText().toString().isEmpty())&&
                (verificationNu.getText().toString().isEmpty())){
          //  Toast.makeText(this, "عفواً بإدخال كل الحقول", Toast.LENGTH_SHORT).show();
        }



        if (uPass.equals(rePassw)){
            getPass();
            if (outhCode.equals(verificationPass)){
                saveLogin();
                saveUserNamePass(uName,rePassw);
                recreate();
//                loginChargingLayout.setVisibility(View.VISIBLE);
//                chargingLayout.setVisibility(View.GONE);
           }
            else {
                Toast.makeText(this, "عفواً غير مصًرح لك استخدام هذا التطبيق", Toast.LENGTH_LONG).show();
                vibrate();
            }
        }
        else {
            Toast.makeText(this, "عفواً كلمة المرور غير متطابقة", Toast.LENGTH_LONG).show();
            vibrate();
        }
    }

    private void saveUserNamePass(String name,String pass) {
        SharedPreferences sharedPreferences=getSharedPreferences("registrationADM",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(USER_REGISTERED_NAME,name);
        editor.putString(USER_REGISTERED_PASS,pass);
        editor.apply();
    }

    private void saveLogin() {
        SharedPreferences sharedPreferences=getSharedPreferences("registrationADM",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(IS_USER_REGISTERED,true);
        editor.apply();
    }
    private void getInputs() {
        uName=userName.getText().toString().trim();
        uPass=password.getText().toString().trim();
        rePassw=rePass.getText().toString().trim();
        outhCode=verificationNu.getText().toString().trim();

    }

    private void chargeNow(int balance,int userId,int requestID) {
        showLoadingDialog();
        Log.e("chargeNow","chargeNow excuted balance= "+balance+" user id= "+userId+" requestID"+requestID);
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());


        Call<Result> call = apiService.updateBalance(balance,userId,requestID,getAdminName(),getDeviceIMEI(),date);
        call.enqueue(new retrofit2.Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                dismissLoadingDialog();
                if (!response.isSuccessful()) {
                    Log.e("error code","Code: " + response.code());
                    textView.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.GONE);
                    textView.setText("لم يتم الشحن بنجاح");
                    button.setText("رجوع");
                    return;
                }
                assert response.body() != null;
                if (response.body().getError()){
                    textView.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.GONE);
                    textView.setText("لم يتم الشحن بنجاح");
                    button.setText("رجوع");

                }
                else {
                    assert response.body() != null;
                    Log.e("charge message",response.body().getMessage());
                    textView.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.GONE);
                    button.setText("رجوع");
                    textView.setText("تم الشحن بنجاح");
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("charge error",t.toString());
                textView.setVisibility(View.VISIBLE);
                textView.setText("لم يتم الشحن بنجاح");
                editText.setVisibility(View.GONE);
                button.setText("رجوع");
                dismissLoadingDialog();
            }
        });

    }



    private void getPass() {

        Log.d("isAdimnAUTH","isAdimnAUTH");
        apiService.getVerificationNumber().enqueue(new retrofit2.Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (!response.isSuccessful()) {
                    Log.e("error code","Code: " + response.code());
                    return;
                }
                assert response.body() != null;
                verificationPass=response.body().getPass();
                Log.d("VerificationNumber",response.body().getPass());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("getVerificatin error",t.toString());

            }
        });
    }
    private boolean isLogIn() {
        SharedPreferences sharedPreferences=getSharedPreferences("registrationADM",MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_USER_REGISTERED,false);
    }
    private String getAdminName() {
        SharedPreferences sharedPreferences=getSharedPreferences("registrationADM",MODE_PRIVATE);
        return sharedPreferences.getString(USER_REGISTERED_NAME,"defulte");
    }
    private String getAdminPass() {
        SharedPreferences sharedPreferences=getSharedPreferences("registrationADM",MODE_PRIVATE);
        return sharedPreferences.getString(USER_REGISTERED_PASS,"defulte");
    }



    @Override
    public void onDialogPositive(String pass) {
        Log.d("passwords","admin "+getAdminPass()+" Entered "+pass);
if (pass.equals(getAdminPass())){
    chargeNow(sendingBalance,id,requestID);

}
else {
    Toast.makeText(this, "غير مصًرح لك إجراء هذة العملية", Toast.LENGTH_SHORT).show();
    vibrate();
}
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
    @Override
    public void editValue(String value) {

    }

    @Override
    public void nameValue(String name) {

    }

    private void showCustomDialog() {
        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.show(getSupportFragmentManager(), "CustomDialogFragment");

    }
    private void checkIfIsTextEmpty() {
        if (userName.getText().toString().trim().isEmpty()) {
            userName.setError(ERROR_MESSAGE);
            vibrate();
        }
        if (password.getText().toString().trim().isEmpty()) {
            password.setError(ERROR_MESSAGE);
            vibrate();
        }
        if (rePass.getText().toString().trim().isEmpty()) {
            rePass.setError(ERROR_MESSAGE);
            vibrate();
        }
        if (verificationNu.getText().toString().trim().isEmpty()) {
            verificationNu.setError(ERROR_MESSAGE);
            vibrate();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        editText.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
    }
    public void showLoadingDialog() {
        mDialog = new MaterialDialog.Builder(UnfinishedRQShowActivity.this)
                .title(R.string.app_name)
                .content(R.string.progress_wait)
                .progress(true, 0)
                .show();
    }


    public void dismissLoadingDialog() {
        if (mDialog!=null){
            mDialog.dismiss();
        }

    }
    public void vibrate() {
        Vibrator vibs = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibs.vibrate(200);
    }
    /**
     * Returns the unique identifier for the device
     *
     * @return unique identifier for the device
     */
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

}