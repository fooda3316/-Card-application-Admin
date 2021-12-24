package com.example.adminapplication.activities.reqests;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;


import com.afollestad.materialdialogs.MaterialDialog;
import com.example.adminapplication.R;
import com.example.adminapplication.model.UnfinishedRQ;
import com.example.adminapplication.adaptors.UnfinishedRQAdaptor;
import com.example.adminapplication.viewmodels.UnfinishedRQVM;

import java.util.List;
import java.util.Objects;

public class UnfinishedRQActivity extends AppCompatActivity {
     private RecyclerView recyclerView;
     private UnfinishedRQVM unfinishedRQVM;
     private UnfinishedRQAdaptor rqAdaptor;
    private MaterialDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLoadingDialog();
        setContentView(R.layout.activity_unfinished_r_q);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView=findViewById(R.id.unfinishedRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rqAdaptor=new UnfinishedRQAdaptor(this);
        recyclerView.setAdapter(rqAdaptor);
        unfinishedRQVM= ViewModelProviders.of(this).get(UnfinishedRQVM.class);
        unfinishedRQVM.getUnfinishedLiveData().observe(this, new Observer<List<UnfinishedRQ>>() {
            @Override
            public void onChanged(List<UnfinishedRQ> unfinishedRQS) {
                if(unfinishedRQS.size()>=1){
                    rqAdaptor.setUnfinishedRQ(unfinishedRQS);

                }

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
    public void showLoadingDialog() {
        mDialog = new MaterialDialog.Builder(UnfinishedRQActivity.this)
                .title(R.string.app_name)
                .content(R.string.progress_wait)
                .progress(true, 0)
                .show();
    }


    public void dismissLoadingDialog() {
        mDialog.dismiss();
    }

    @Override
    protected void onStop() {
        super.onStop();
        recreate();
    }
}