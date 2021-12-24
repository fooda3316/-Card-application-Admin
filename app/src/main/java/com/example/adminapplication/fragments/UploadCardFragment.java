package com.example.adminapplication.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.adminapplication.R;
import com.example.adminapplication.api.APIService;
import com.example.adminapplication.model.Result;
import com.example.adminapplication.network.RetrofitCardClient;
import com.example.adminapplication.network.RetrofitClient;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class UploadCardFragment extends Fragment {
    private Button button;
    private EditText cardName, cardCatigory, cardPrice;
    private String name,catigory,price;
    private ImageView imageButton;
    private Uri selectedImage=null;
    private APIService apiService;
    private MaterialDialog mDialog;
    private static final int PICK_IMAGE_REQUEST=1;
    private static final int PERMISSION_REQUEST_CODE=1;
 public UploadCardFragment() {
        // Required empty public constructor
    }
    public static UploadCardFragment newInstance() {

        return new UploadCardFragment();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button =view.findViewById(R.id.btnAddCard);
        cardName =view.findViewById(R.id.card_name);
        cardCatigory =view.findViewById(R.id.card_ctegory_id);
        cardPrice =view.findViewById(R.id.card_price);
        imageButton=view.findViewById(R.id.selectedCardImage);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImage ==null){
                    if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)){
                        openFileChooser();
                    }
                    else {
                        requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                }
            }
        });
        name=cardName.getText().toString().trim();
        catigory=cardCatigory.getText().toString().trim();
        price=cardPrice.getText().toString().trim();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImage!=null){
                    showLoadingDialog();

                    addCard("3","466","name",selectedImage);
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


    private void openFileChooser() {
        Intent intent = new Intent();
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }
    private void showAlert(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
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
        mDialog = new MaterialDialog.Builder(getContext())
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
        Log.d("FragmentA.java","onActivityResult called");

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            //the image URI
            selectedImage = data.getData();
            imageButton.setImageURI(selectedImage);

        }
    }


    private void addCard(String categoryID,String price,String imageName,Uri imageUri){
     Log.e("info" ,"categoryID "+categoryID+" price "+price+" imageName"+imageName);

        File file = new File(getRealPathFromURI(imageUri));
        RequestBody category_id = RequestBody.create(MediaType.parse("text/plain"), categoryID);
        RequestBody card_price = RequestBody.create(MediaType.parse("text/plain"), price);
        RequestBody imageBody = RequestBody.create(MediaType.parse("text/plain"), imageName);
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContext().getContentResolver().getType(imageUri)), file);

        apiService= RetrofitCardClient.getInstance().getApiService();
        Call<Result> call=apiService.uploadCard(requestFile,category_id,imageBody,card_price);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                if (!response.isSuccessful()) {
                    showAlert("حدث خطأ اثناء إضافة الصورة");
                    dismissLoadingDialog();
                    Log.e("error code","Code: " + response.code());
                    return;
                }
                assert response.body() != null;
                if (!response.body().getError()){
                    Log.d("Success Message",response.body().getMessage());

                    dismissLoadingDialog();
                    showAlert("تمت إضافة الصورة ينجاح");
                }
                dismissLoadingDialog();
                Log.e("Message",response.body().getMessage());


            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("onFailure",t.toString());
                dismissLoadingDialog();
                showAlert("حدث خطأ اثناء إضافة الصورة");
            }
        });

    }
    private String getRealPathFromURI(Uri contentUri) {
        Log.e("getRealPath","getRealPath");
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
    public boolean checkPermission(String permission) {
        if (ContextCompat.checkSelfPermission(getActivity(),permission )
                != PackageManager.PERMISSION_GRANTED) {

            return false;
        }
        return true;
    }
    public void requestPermission(String permission) {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{permission},
                PERMISSION_REQUEST_CODE);
    }
}