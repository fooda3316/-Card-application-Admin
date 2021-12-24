package com.example.adminapplication.activities.cards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.adminapplication.R;
import com.example.adminapplication.adaptors.PagesAdaptor;
import com.example.adminapplication.adaptors.PrivetCardsMainAdaptor;
import com.example.adminapplication.utils.Data;

public class PrivetCardsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PrivetCardsMainAdaptor adaptor;
    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privet_cards);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.privetRV);
        data=new Data();
        adaptor=new PrivetCardsMainAdaptor(this,data.mainList());
        recyclerView.setAdapter(adaptor);
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