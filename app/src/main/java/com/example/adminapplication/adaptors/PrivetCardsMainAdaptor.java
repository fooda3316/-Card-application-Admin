package com.example.adminapplication.adaptors;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminapplication.R;
import com.example.adminapplication.activities.cards.BranchActivity;
import com.example.adminapplication.activities.cards.SubCardsActivity;
import com.example.adminapplication.model.Home;
import com.example.adminapplication.model.Page;

import java.util.ArrayList;
import java.util.List;

import static com.example.adminapplication.utils.Constants.BRANCH_KEY;
import static com.example.adminapplication.utils.Constants.BRANCH_TITLE_KEY;

public class PrivetCardsMainAdaptor extends RecyclerView.Adapter<PrivetCardsMainAdaptor.PurchaseVH> {

    private List<Home> homeList ;
    private Context context;

    public PrivetCardsMainAdaptor(Context context,List<Home> list) {
        this.context=context;
        this.homeList = list;
    }

    @NonNull
    @Override
    public PurchaseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_privete_cards_main, parent, false);
        return new PurchaseVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseVH holder, final int position) {
        holder.main_name.setText(homeList.get(position).getTitle());
        holder.main_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(homeList.get(position));
            }
        });

  }



    @Override
    public int getItemCount() {
        return homeList.size();
    }


  static   class PurchaseVH extends RecyclerView.ViewHolder{

        private TextView main_name;
        public PurchaseVH(@NonNull View itemView) {
            super(itemView);
            main_name =itemView.findViewById(R.id.private_main_name);
        }
    }
    private void sendData(Home home) {
        Log.e("image",home.getTitle());
        Intent intent;
        if (home.getHaveBranch()){
            intent = new Intent(context, BranchActivity.class);
            intent.putExtra(BRANCH_TITLE_KEY,home.getTitle());
            context.startActivity(intent);

        }
        else {
            context.startActivity(new Intent(context, SubCardsActivity.class).putExtra(BRANCH_KEY,"a").putExtra(BRANCH_TITLE_KEY,home.getTitle()));

        }


    }
}
