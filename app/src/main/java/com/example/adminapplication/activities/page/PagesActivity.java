package com.example.adminapplication.activities.page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.adminapplication.R;
import com.example.adminapplication.adaptors.PagesAdaptor;
import com.example.adminapplication.api.APIService;
import com.example.adminapplication.model.Page;
import com.example.adminapplication.model.Result;
import com.example.adminapplication.network.RetrofitClient;
import com.example.adminapplication.viewmodels.PageViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagesActivity extends AppCompatActivity {
    private PageViewModel pageViewModel;
    private MaterialDialog mDialog;
    private RecyclerView recyclerView;
    private PagesAdaptor adaptor;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pages);
        apiService = RetrofitClient.getInstance().getApiService();
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        showLoadingDialog();
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        recyclerView = findViewById(R.id.pagesRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptor = new PagesAdaptor();
        recyclerView.setAdapter(adaptor);
        deleteSwipe(recyclerView);
        pageViewModel.getCardsLiveData().observe(this, new Observer<List<Page>>() {
            @Override
            public void onChanged(List<Page> pages) {
                adaptor.setPages(pages);
                dismissLoadingDialog();

            }
        });
    }

    public void showLoadingDialog() {
        mDialog = new MaterialDialog.Builder(PagesActivity.this)
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
                String name = adaptor.getPageAt(poss).getName();
                showAlert(name);
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void deletePage(String name) {
        showLoadingDialog();
        Call<Result> call = apiService.deletePage(name);
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
                    Toast.makeText(PagesActivity.this, "تم الحذف", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("AdminFailure", t.toString());
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
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void showAlert(String name) {
        AlertDialog.Builder alert = new AlertDialog.Builder(PagesActivity.this);
        alert
                .setIcon(android.R.drawable.stat_notify_error)
                .setTitle("مسح")
                .setMessage(" هل ترغب فعلاً في مسح " + name)
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
                        deletePage(name);
                        //do some thing
                    }
                }).show();
    }
}