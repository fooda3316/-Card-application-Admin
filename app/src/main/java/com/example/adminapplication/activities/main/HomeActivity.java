package com.example.adminapplication.activities.main;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.adminapplication.R;
import com.example.adminapplication.activities.reqests.UnfinishedRQActivity;
import com.example.adminapplication.activities.admin.AdminActivity;
import com.example.adminapplication.activities.users.AllUsersActivity;
import com.example.adminapplication.api.APIService;
import com.example.adminapplication.dialog.CustomAdminsDialogFragment;
import com.example.adminapplication.dialog.CustomDialogFragment;
import com.example.adminapplication.dialog.CustomDialogListener;
import com.example.adminapplication.model.Ime;
import com.example.adminapplication.model.Result;
import com.example.adminapplication.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements CustomDialogListener {
    private RelativeLayout layoutAdmins,layoutCharge,allusers,cardsLayout,pagesLayout;
    private APIService apiService;
    private String verificationPass="";
    private Boolean isAdminHaveIme=false;
    private String destination="";
    private MaterialDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Log.e("ime", getDeviceIMEI());
        layoutAdmins = findViewById(R.id.layout_admins);
        layoutCharge = findViewById(R.id.unfinishedRQA);
        allusers = findViewById(R.id.allUsersLayout);
        cardsLayout = findViewById(R.id.layout_cards);

        apiService = RetrofitClient.getInstance().getApiService();
//        Log.e("IME", getDeviceIMEI());
        if (!isFirstLunch() || getAdminName().equals("")) {
            showAdminsDialog();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (checkPermission()) {
                getAdminIme();
                getPass();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);

            }
          //  getAdminIme();

        }
        else {



        if (checkPermission()) {
            getAdminIme();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);

        }
        if (checkPermission()) {
            getPass();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);

        }
        }
        apiService = RetrofitClient.getInstance().getApiService();
        layoutCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdminHaveIme){
                    startActivity(new Intent(HomeActivity.this, UnfinishedRQActivity.class));
                }
                else {
                    showAlert();
                    vibrate(400);
                }

            }
        });
        layoutAdmins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCustomDialog();
                destination="admins";
            }
        });
        allusers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
                destination="users";
            }
        });
        cardsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
                destination="cards";
            }
        });
    }
    private void getPass() {
       // if (checkPermission(Manifest.permission.))

        //Log.d("isAdimnAUTH","isAdimnAUTH");
        apiService.getManagerKeyIme(getDeviceIMEI()).enqueue(new retrofit2.Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (!response.isSuccessful()) {
                    Log.e("error code","Code: " + response.code());
                    return;
                }
                assert response.body() != null;
                if (response.body().getMessage().equals("allowed")){
                    verificationPass=response.body().getPass();
                }

//                Log.d("VerificationNumber",response.body().getPass());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("getVerificatin error",t.toString());

            }
        });
    }

    @Override
    public void onDialogPositive(String pass) {
        if (pass.equals(verificationPass)){
          if (destination.equals("admins")){
              startActivity(new Intent(HomeActivity.this, AdminActivity.class));
          }
          else if (destination.equals("users")) {
              startActivity(new Intent(HomeActivity.this, AllUsersActivity.class));

          }
          else if (destination.equals("cards")) {
              startActivity(new Intent(HomeActivity.this, ControlActivity.class));

          }


        }
        else {
            Toast.makeText(this, "عفواً لا يمكنك فتح هذة الصفحة", Toast.LENGTH_SHORT).show();
            vibrate(200);
        }
    }


    @Override
    public void editValue(String value) {

    }

    @Override
    public void nameValue(String name) {
        saveAdminName(name);
    }

    private void showCustomDialog() {
        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.show(getSupportFragmentManager(), "CustomDialogFragment");

    }
    private void showAdminsDialog() {
        CustomAdminsDialogFragment dialog = new CustomAdminsDialogFragment();
        dialog.show(getSupportFragmentManager(), "CustomAdminsDialogFragment");

    }
    public void vibrate(int duration) {
        Vibrator vibs = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibs.vibrate(duration);
    }
    /**
     * Returns the unique identifier for the device
     *
     * @return unique identifier for the device
     */
    public String getDeviceIMEI() {
        String deviceUniqueIdentifier = null;
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            deviceUniqueIdentifier = tm.getDeviceId();
        }
        if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
            deviceUniqueIdentifier = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceUniqueIdentifier;
    }
    private void getAdminIme() {

       // Log.d("isAdimnAUTH","isAdimnAUTH");
        apiService.getAdminIme(getDeviceIMEI()).enqueue(new retrofit2.Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (!response.isSuccessful()) {
                    Log.e("error code","Code: " + response.code());
                    return;
                }
                assert response.body() != null;
                if (!response.body().getError()){
                    isAdminHaveIme=response.body().getIme();
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("getVerificatin error",t.toString());

            }
        });
    }
    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }
    public void showAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
        alert
                .setIcon(android.R.drawable.stat_notify_error)
                .setTitle("تسجيل")
                .setMessage("عفواً هاتفك غير مسجل هل ترغب في تسجيل هاتفك")
                .setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        registeringAdmin(new Ime(getDeviceIMEI(),getAdminName()));
                        //do some thing
                    }
                }).show();
    }
    private Boolean isFirstLunch(){
        SharedPreferences sharedPreferences=getSharedPreferences("FirstLunch", Context.MODE_PRIVATE);
        return  sharedPreferences.getBoolean("isFirstLunch",false);
    }
    private String getAdminName(){
        SharedPreferences sharedPreferences=getSharedPreferences("Admins", Context.MODE_PRIVATE);
        return  sharedPreferences.getString("AdminName","");
    }
    private void saveAdminName(String name) {
        Log.e("name",name);
        confirmFirstLunch();
        SharedPreferences sharedPreferences=getSharedPreferences("Admins",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("AdminName",name);
        editor.apply();
    }

    private void confirmFirstLunch(){
        SharedPreferences sharedPreferences=getSharedPreferences("FirstLunch",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("isFirstLunch",true);
        editor.apply();

    }
    private void registeringAdmin(Ime ime) {
        showLoadingDialog();
        apiService= RetrofitClient.getInstance().getApiService();
        // Log.e("card data","name  "+card.getTitle()+" SubName "+card.getSubName()+" Serialnumber "+card.getSerialnumber()+" value "+card.getValue());
        Call<Result> call=apiService.addAdminIme(ime.getIme(),ime.getName());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (!response.isSuccessful()) {
                    Log.e("error code","Code: " + response.code());
                    Toast.makeText(HomeActivity.this, "لم يتم إرسال الطلب بنجاح", Toast.LENGTH_LONG).show();
                    return;
                }
                assert response.body() != null;
                if(!response.body().getError()) {
                    dismissLoadingDialog();
                    Log.d("Message", response.body().getMessage());
                    Toast.makeText(HomeActivity.this, "تم إرسال الطلب بنجاح", Toast.LENGTH_LONG).show();
                    recreate();
                }
                else {
                    dismissLoadingDialog();
                    Toast.makeText(HomeActivity.this, "لم يتم إرسال الطلب بنجاح", Toast.LENGTH_LONG).show();
                    vibrate(200);

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("Add admin card error",t.toString());
                dismissLoadingDialog();
                Toast.makeText(HomeActivity.this, "لم تتم إضافة البطاقة بنجاح", Toast.LENGTH_LONG).show();
                vibrate(200);
            }
        });
    }
    public void showLoadingDialog() {
        mDialog = new MaterialDialog.Builder(HomeActivity.this)
                .title(R.string.app_name)
                .content(R.string.progress_wait)
                .progress(true, 0)
                .show();
    }
    public void dismissLoadingDialog() {
        mDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    public static boolean checkPermission(String permission, Context context) {
        int permissionCheck = ContextCompat.checkSelfPermission(context, permission);
        return (permissionCheck == PackageManager.PERMISSION_GRANTED);
    }
}