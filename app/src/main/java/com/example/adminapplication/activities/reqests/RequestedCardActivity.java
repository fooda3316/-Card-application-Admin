package com.example.adminapplication.activities.reqests;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
import com.example.adminapplication.adaptors.RequestAdaptor;
import com.example.adminapplication.api.APIService;
import com.example.adminapplication.model.Request;
import com.example.adminapplication.model.Result;
import com.example.adminapplication.network.RetrofitClient;
import com.example.adminapplication.viewmodels.RequestedCardsVM;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestedCardActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RequestAdaptor adaptor;
    private MaterialDialog mDialog;
    private RequestedCardsVM cardsVM;
    private APIService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_card);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        apiService = RetrofitClient.getInstance().getApiService();
        showLoadingDialog();
        cardsVM = ViewModelProviders.of(this).get(RequestedCardsVM.class);
        recyclerView = findViewById(R.id.requestCardsRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptor = new RequestAdaptor(this);
        recyclerView.setAdapter(adaptor);
        deleteSwipe(recyclerView);
        cardsVM.getUnfinishedLiveData().observe(this, new Observer<List<Request>>() {
            @Override
            public void onChanged(List<Request> requests) {
                adaptor.setRequests(requests);
                dismissLoadingDialog();

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
    public void showLoadingDialog() {
        mDialog = new MaterialDialog.Builder(RequestedCardActivity.this)
                .title(R.string.app_name)
                .content(R.string.progress_wait)
                .progress(true, 0)
                .show();
    }
    public void dismissLoadingDialog() {
        mDialog.dismiss();
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
                adaptor.notifyDataSetChanged();
                int poss = viewHolder.getLayoutPosition();
                int id = adaptor.getRequestAt(poss).getId();
                deleteRequest(id);
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void deleteRequest(int  id) {
        showLoadingDialog();
        Call<Result> call = apiService.deleteRequest(id);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (!response.isSuccessful()) {
                    Log.e("error code", "Code: " + response.code());
                    return;
                }
                if (!response.body().getError()) {
                    dismissLoadingDialog();
                    recreate();
                    Toast.makeText(RequestedCardActivity.this, "تم الحذف بنجاح", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("AdminFailure", t.toString());
                dismissLoadingDialog();
            }
        });
    }
}