package com.example.adminapplication.adaptors;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminapplication.R;
import com.example.adminapplication.activities.cards.AllCardsShowActivity;
import com.example.adminapplication.model.Card;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AllCardsAdaptor extends RecyclerView.Adapter<AllCardsAdaptor.PurchaseVH> {

    private List<Card> users=new ArrayList<>();
    private Context context;

    String cardFinalTitle,cardTitle;
    public AllCardsAdaptor(Context context) {
        Log.e("Adaptor","AllCardsAdaptor");
        this.context=context;

    }

    @NonNull
    @Override
    public PurchaseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_all_cards, parent, false);
        return new PurchaseVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseVH holder, final int position) {
         cardFinalTitle =users.get(position).getSubName()+" "+ getCardTitle(users.get(position).getTitle())+" "+ getCardBranch(users.get(position).getBranch());
        holder.name.setText(cardFinalTitle);
        String balanceV=users.get(position).getPrice()+"";
        holder.value.setText(balanceV);
       // holder.balance.setText("balance");
        //Log.e("allCards adaptor image","https://onlinesd.store/billing/images/"+users.get(position).getImage());
        Picasso.get().load("https://onlinesd.store/billing/ImageUploadApi/uploads/cards/"+users.get(position).getImage()).error(R.drawable.ic_error).into(holder.imageView, new Callback() {

            //  Picasso.get().load("http://10.0.2.2/photos/"+users.get(position).getImage()).error(R.drawable.ic_error).into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                Log.d("Error : ", e.getMessage());
            }
        });

        holder.admin_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData( users.get(position));
            }
        });
       // holder.admin_layout.setBackgroundResource(backgrounds[holder.backgroundIndex]);

        //   Log.e("balance",sellHistories.get(position).getBalance()+"");
  }

    private void sendData(Card admin) {
      Intent intent = new Intent(context, AllCardsShowActivity.class);
        Log.e("cardTitle", cardFinalTitle +"");
        intent.putExtra("name",admin.getTitle());
        intent.putExtra("sub name",admin.getSubName());
        intent.putExtra("branch",admin.getBranch());

        intent.putExtra("id",admin.getId());
        intent.putExtra("image",admin.getImage());
        intent.putExtra("balance",admin.getPrice());

        context.startActivity(intent);
        Log.e("Adaptor data","sub name "
                +admin.getSubName()+"branch "+admin.getBranch()+" name "+admin.getTitle());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    public void setAdmins(List<Card> users) {
        this.users = users;
        notifyDataSetChanged();
    }

  static   class PurchaseVH extends RecyclerView.ViewHolder{

        private TextView name, value;
        private LinearLayout admin_layout;
        private ImageView imageView;
        private ProgressBar progressBar;
        public PurchaseVH(@NonNull View itemView) {
            super(itemView);
            name =itemView.findViewById(R.id.cardTitle);
            admin_layout=itemView.findViewById(R.id.adminLin);
            value =itemView.findViewById(R.id.cardPrice);
            imageView=itemView.findViewById(R.id.clint_image);
            progressBar=itemView.findViewById(R.id.clintProgress);
        }
    }
    private String getCardBranch(String branch){

         String card_branch="";
        if (branch.equals("a")){
            card_branch="";
        }
        else {
            card_branch=branch;
        }
        return card_branch;

    }

    private String getCardTitle(String title){

        if (!title.contains("Months")){
            cardTitle=title+" $";
        }
        else {
            cardTitle=title;
        }
        return cardTitle;

    }

}
