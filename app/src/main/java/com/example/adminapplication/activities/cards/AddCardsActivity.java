package com.example.adminapplication.activities.cards;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.adminapplication.R;
import com.example.adminapplication.adaptors.AllCardsAdminAdaptor;
import com.example.adminapplication.model.AdminCard;
import com.example.adminapplication.viewmodels.AllCardsAdminVM;

import java.util.List;


public class AddCardsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AllCardsAdminVM allCardsAdminVM;
    private AllCardsAdminAdaptor adaptor;
    private Button button;
  @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cards);
      ActionBar actionBar = getSupportActionBar();
      if (actionBar != null) {
          actionBar.setDisplayHomeAsUpEnabled(true);
      }

      recyclerView=findViewById(R.id.addCardsRV);

      adaptor=new AllCardsAdminAdaptor();
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      recyclerView.setAdapter(adaptor);
      allCardsAdminVM= ViewModelProviders.of(this).get(AllCardsAdminVM.class);
      allCardsAdminVM.getUnfinishedLiveData().observe(this, new Observer<List<AdminCard>>() {
          @Override
          public void onChanged(List<AdminCard> adminCards) {
              Log.d(" cards size",adminCards.size()+"");
              adaptor.setAdmins(adminCards);
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



}