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
import com.example.adminapplication.activities.admin.AdminsShowActivity;
import com.example.adminapplication.model.Admin;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdminsAdaptor extends RecyclerView.Adapter<AdminsAdaptor.PurchaseVH> {

    private List<Admin> admins=new ArrayList<>();
    private Context context;


    public AdminsAdaptor(Context context) {
       // Log.e("Adaptor","AdminsAdaptor");
        this.context=context;

    }

    @NonNull
    @Override
    public PurchaseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_admins, parent, false);
        return new PurchaseVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseVH holder, final int position) {
        holder.adminName.setText(admins.get(position).getName());
        holder.date.setText(admins.get(position).getDate());
        holder.balance.setText(admins.get(position).getBalance());
       Log.e("admins adaptor image","name is :"+admins.get(position).getName()+" \n date is"+admins.get(position).getDate()+" \n balance is "+admins.get(position).getBalance());

        // Log.e("admins adaptor image","https://onlinesd.store/billing/images/"+admins.get(position).getImage());
        Picasso.get().load("https://onlinesd.store/billing/ImageUploadApi/uploads/improvement/"+admins.get(position).getImage()).error(R.drawable.ic_error).into(holder.imageView, new Callback() {

      //  Picasso.get().load("http://10.0.2.2/photos/"+admins.get(position).getImage()).error(R.drawable.ic_error).into(holder.imageView, new Callback() {
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
                sendData( admins.get(position));
            }
        });
       // holder.admin_layout.setBackgroundResource(backgrounds[holder.backgroundIndex]);

        //   Log.e("balance",sellHistories.get(position).getBalance()+"");
  }

    private void sendData(Admin admin) {
      Intent intent = new Intent(context, AdminsShowActivity.class);
        intent.putExtra("date",admin.getDate());
        intent.putExtra("name",admin.getName());
        intent.putExtra("image",admin.getImage());
        intent.putExtra("balance",admin.getBalance());

        context.startActivity(intent);
      //  Log.d("Adaptor data","id "+rq.getId()+" userID "+rq.getUserId());
    }

    @Override
    public int getItemCount() {
        return admins.size();
    }
    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
        notifyDataSetChanged();
    }

  static   class PurchaseVH extends RecyclerView.ViewHolder{

        private TextView adminName,date,balance;
        private LinearLayout admin_layout;
        private ImageView imageView;
        private ProgressBar progressBar;
        public PurchaseVH(@NonNull View itemView) {
            super(itemView);
            adminName=itemView.findViewById(R.id.clintName);
            admin_layout=itemView.findViewById(R.id.adminLin);
            date=itemView.findViewById(R.id.clintDate);
            balance=itemView.findViewById(R.id.clintBalance);
            imageView=itemView.findViewById(R.id.clint_image);
            progressBar=itemView.findViewById(R.id.clintProgress);
        }
    }

}
