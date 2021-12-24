package com.example.adminapplication.activities.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.adminapplication.R;
import com.example.adminapplication.activities.admin.AllCardsAddminActivity;
import com.example.adminapplication.activities.cards.AddCardsActivity;
import com.example.adminapplication.activities.ime.ImeActivity;
import com.example.adminapplication.activities.reqests.RequestedCardActivity;
import com.example.adminapplication.activities.cards.PrivetCardsActivity;
import com.example.adminapplication.activities.cards.ShowCardsActivity;
import com.example.adminapplication.activities.page.PagesActivity;
import com.example.adminapplication.activities.page.UploadPageActivity;

public class ControlActivity extends AppCompatActivity {
    private RelativeLayout allCards,addCard,showPages,addPage,requests,ime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_control);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        allCards=findViewById(R.id.allCardsLayout);
        addCard=findViewById(R.id.addCardLayout);
        showPages=findViewById(R.id.showPagesLayout);
        addPage=findViewById(R.id.addPageLayout);
        showPages=findViewById(R.id.showPagesLayout);
        requests=findViewById(R.id.requestCardsLayout);
        ime =findViewById(R.id.imeLayout);
        ime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ControlActivity.this, ImeActivity.class));
            }
        });
        allCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ControlActivity.this, ShowCardsActivity.class));
            }
        });
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ControlActivity.this, AllCardsAddminActivity.class));

            }
        });
addPage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(ControlActivity.this, UploadPageActivity.class));

    }
});
        requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ControlActivity.this, RequestedCardActivity.class));

            }
        });
showPages.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(ControlActivity.this, PagesActivity.class));

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