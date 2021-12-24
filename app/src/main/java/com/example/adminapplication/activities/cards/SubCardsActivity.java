package com.example.adminapplication.activities.cards;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.adminapplication.R;
import com.example.adminapplication.adaptors.AllCardsAdapter;
import com.example.adminapplication.model.Card;
import com.example.adminapplication.viewmodels.SubCardViewModel;

import java.util.List;

import static com.example.adminapplication.utils.Constants.BRANCH_KEY;
import static com.example.adminapplication.utils.Constants.BRANCH_TITLE_KEY;

public class SubCardsActivity extends AppCompatActivity {
    private AllCardsAdapter mAdapter;
    private RecyclerView recyclerView;
    private MaterialDialog mDialog;
    private SubCardViewModel viewModel;
    private String title,branch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_cards);
        ActionBar actionBar = getSupportActionBar();
        Intent intent = getIntent();
        branch = intent.getStringExtra(BRANCH_KEY);
        title = intent.getStringExtra(BRANCH_TITLE_KEY);
        showLoadingDialog();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        actionBar.setTitle(branch+" "+title);
        viewModel = ViewModelProviders.of(SubCardsActivity.this).get(SubCardViewModel.class);
        Log.e("card data", "title is " + title + " branch is " + branch);
        viewModel.getCardsLiveData(title, branch).observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
                setUpRV(cards);
                dismissLoadingDialog();
            }
        });
    }

    private void setUpRV(List<Card> cardsList){
    recyclerView = findViewById(R.id.product_rv);
    mAdapter = new AllCardsAdapter(cardsList, SubCardsActivity.this);
    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
    recyclerView.setLayoutManager(mLayoutManager);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setAdapter(mAdapter);

}
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void showLoadingDialog() {
        mDialog = new MaterialDialog.Builder(SubCardsActivity.this)
                .title(R.string.app_name)
                .content(R.string.progress_wait)
                .progress(true, 0)
                .show();
    }
    public void dismissLoadingDialog() {
        mDialog.dismiss();
    }
}