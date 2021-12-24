package com.example.adminapplication.activities.page;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.adminapplication.R;
import com.example.adminapplication.api.APIService;
import com.example.adminapplication.model.Result;
import com.example.adminapplication.network.RetrofitPageClient;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class UploadPageActivity extends AppCompatActivity {
    private Button button;
    private EditText pageName,  pageUri,txtTest1;
    private String name, pagePath;
    private ImageView imageButton;
    private Uri selectedImage=null;
    private APIService apiService;
    private MaterialDialog mDialog;

    private static final int PICK_IMAGE_REQUEST=1;
    private static final int PERMISSION_REQUEST_CODE=1;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);
        button =findViewById(R.id.btnAddCard);
        pageName =findViewById(R.id.page_name);

        pageUri =findViewById(R.id.page_uri);
        imageButton=findViewById(R.id.selectedCardImage);


        //txtTest1=findViewById(R.id. txtTest);

        Log.e("is Path null ",(pagePath==null)+"");
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImage ==null){
                    if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)){
                        openFileChooser();
                      //  Log.e("txtTest1",txtTest1.getText().toString().trim());
                    }
                    else {
                        requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImage!=null){
                    showLoadingDialog();
                    if (pageName.getText().toString().isEmpty()){
                        pageName.setError("لا يمكن ترك هذا الحقل فارغاً");
                    }
                        name= pageName.getText().toString().trim();
                    if (pageUri.getText().toString().isEmpty()){
                        pageUri.setError("لا يمكن ترك هذا الحقل فارغاً");
                    }
                    pagePath = pageUri.getText().toString().trim();
                    sendPicture(pagePath,name,selectedImage);
                }
                else {
                    if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)){
                        openFileChooser();
                    }
                    else {
                        requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                }
            }
        });
    }

    private void sendPicture(String uri,String imageName,Uri imageUri){
        Log.e("sendPicture","sendPicture imageName "+imageName+" uri "+uri);
        File file = new File(getRealPathFromURI(imageUri));
        RequestBody uriBody = RequestBody.create(MediaType.parse("text/plain"),uri);
        RequestBody imageBody = RequestBody.create(MediaType.parse("text/plain"), imageName);
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri)), file);

        apiService= RetrofitPageClient.getInstance().getApiService();
        Call<Result> call=apiService.uploadPage(requestFile,imageBody,uriBody );
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                if (!response.isSuccessful()) {
                    dismissLoadingDialog();
                    Log.e("error code","Code: " + response.code());
                    return;
                }
                assert response.body() != null;
                showResult(response.body().getMessage());
                if (!response.body().getError()){
                    Log.d("Success Message",response.body().getMessage());
                    Toast.makeText(UploadPageActivity.this, "تم تحميل الصفحة بنجاح", Toast.LENGTH_SHORT).show();
                    dismissLoadingDialog();
                    recreate();
                }
                dismissLoadingDialog();
                Toast.makeText(UploadPageActivity.this, "لم يتم تحميل الصفحة بنجاح", Toast.LENGTH_SHORT).show();
                Log.e("Message",response.body().getMessage());

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("onFailure",t.toString());
                dismissLoadingDialog();
            }
        });

    }

    private void showResult(String message){
        showAlert(message);
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }
    private void showAlert(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(message)
                .setIcon(android.R.drawable.stat_notify_error)
                .setTitle("Alert")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //do some thing
                    }
                }).show();
    }
    public void showLoadingDialog() {
        mDialog = new MaterialDialog.Builder(this)
                .title(R.string.app_name)
                .content(R.string.progress_wait)
                .progress(true, 0)
                .show();
    }


    public void dismissLoadingDialog() {
        mDialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode,  resultCode,  data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            //the image URI
            selectedImage = data.getData();
            imageButton.setImageURI(selectedImage);

        }
    }


    private String getRealPathFromURI(Uri contentUri) {
        Log.e("getRealPath","getRealPath");
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
    public boolean checkPermission(String permission) {
        if (ContextCompat.checkSelfPermission(this,permission )
                != PackageManager.PERMISSION_GRANTED) {

            return false;
        }
        return true;
    }
    public void requestPermission(String permission) {
        ActivityCompat.requestPermissions(this,
                new String[]{permission},
                PERMISSION_REQUEST_CODE);
    }
}