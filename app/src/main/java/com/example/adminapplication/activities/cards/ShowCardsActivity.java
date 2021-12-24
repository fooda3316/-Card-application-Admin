package com.example.adminapplication.activities.cards;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.adminapplication.R;
import com.example.adminapplication.adaptors.AllCardsAdaptor;
import com.example.adminapplication.model.Card;
import com.example.adminapplication.viewmodels.AllCardsVM;

import java.util.List;



public class ShowCardsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AllCardsVM allCardsVM;
    private AllCardsAdaptor adaptor;
    private MaterialDialog mDialog;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cards);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        showLoadingDialog();
        recyclerView=findViewById(R.id.showCardsRV);
        adaptor=new AllCardsAdaptor(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptor);
        allCardsVM= ViewModelProviders.of(this).get(AllCardsVM.class);
        allCardsVM.getUnfinishedLiveData().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
            //    Log.d(" cards size",cards.size()+"");
                adaptor.setAdmins(cards);
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
        mDialog = new MaterialDialog.Builder(ShowCardsActivity.this)
                .title(R.string.app_name)
                .content(R.string.progress_wait)
                .progress(true, 0)
                .show();
    }
    public void dismissLoadingDialog() {
        mDialog.dismiss();
    }
}