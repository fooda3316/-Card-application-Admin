package com.example.adminapplication.activities.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.adminapplication.R;
import com.example.adminapplication.adaptors.AdminsAdaptor;
import com.example.adminapplication.api.APIService;
import com.example.adminapplication.model.Admin;
import com.example.adminapplication.model.Result;
import com.example.adminapplication.network.RetrofitClient;
import com.example.adminapplication.viewmodels.AdmisVM;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {
    private AdmisVM admisVM;
    private RecyclerView recyclerView;
    private AdminsAdaptor adaptor;
    private APIService apiService;
    private MaterialDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        showLoadingDialog();
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.adminsRV);
        apiService = RetrofitClient.getInstance().getApiService();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptor = new AdminsAdaptor(this);
        recyclerView.setAdapter(adaptor);
        deleteSwipe(recyclerView);
        admisVM = ViewModelProviders.of(this).get(AdmisVM.class);
        admisVM.getUnfinishedLiveData().observe(this, new Observer<List<Admin>>() {
            @Override
            public void onChanged(List<Admin> admins) {
                adaptor.setAdmins(admins);
                dismissLoadingDialog();
            }
        });
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

    private void deleteSwipe(RecyclerView recyclerView) {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                deleteAdmin(viewHolder.getLayoutPosition());
                // Log.e()
                adaptor.notifyDataSetChanged();

            }
        }).attachToRecyclerView(recyclerView);
    }

    private void deleteAdmin(int pos) {
        int id =pos+1;
        showLoadingDialog();
        Call<Result> call = apiService.deleteAdmin(id);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
               dismissLoadingDialog();
                if (!response.isSuccessful()) {
                    dismissLoadingDialog();

                    Log.e("error code", "Code: " + response.code());
                    return;
                }
                if (!response.body().getError()) {
                    dismissLoadingDialog();
                    Toast.makeText(AdminActivity.this, "تم الحذف", Toast.LENGTH_SHORT).show();
                  //  recreate();
                }
                dismissLoadingDialog();

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("AdminFailure", t.toString());
                dismissLoadingDialog();
            }
        });
    }

    public void showLoadingDialog() {
        mDialog = new MaterialDialog.Builder(AdminActivity.this)
                .title(R.string.app_name)
                .content(R.string.progress_wait)
                .progress(true, 0)
                .show();
    }

    public void dismissLoadingDialog() {
        mDialog.dismiss();
    }
}