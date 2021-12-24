package com.example.adminapplication.adaptors;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminapplication.R;
import com.example.adminapplication.model.AdminCard;

import java.util.ArrayList;
import java.util.List;

public class AllCardsAdminAdaptor extends RecyclerView.Adapter<AllCardsAdminAdaptor.PurchaseVH> {

    private List<AdminCard> users=new ArrayList<>();


    public AllCardsAdminAdaptor() {
        Log.e("Adaptor","AllCardsAdminAdaptor");
    }

    @NonNull
    @Override
    public PurchaseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_all_cards_admin, parent, false);
        return new PurchaseVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseVH holder, final int position) {
        holder.adminName.setText(users.get(position).getTitle());
        holder.number.setText(users.get(position).getSerialnumber()+"");
       // String balanceV=users.get(position).getValue()+"";
       // holder.balance.setText(balanceV);
       // holder.balance.setText("balance");



       // holder.admin_layout.setBackgroundResource(backgrounds[holder.backgroundIndex]);

        //   Log.e("balance",sellHistories.get(position).getBalance()+"");
  }



    @Override
    public int getItemCount() {
        return users.size();
    }
    public void setAdmins(List<AdminCard> users) {
        this.users = users;
        notifyDataSetChanged();
    }

  static   class PurchaseVH extends RecyclerView.ViewHolder{

        private TextView adminName,number,phone,balance;
        private LinearLayout admin_layout;

        public PurchaseVH(@NonNull View itemView) {
            super(itemView);
            adminName=itemView.findViewById(R.id.cardTitle);
            number=itemView.findViewById(R.id.cardNumber);
            admin_layout=itemView.findViewById(R.id.adminLin);
            balance=itemView.findViewById(R.id.cardAdminPrice);

        }
    }

}
