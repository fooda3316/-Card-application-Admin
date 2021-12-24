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
import com.example.adminapplication.activities.users.ShowUserActivity;
import com.example.adminapplication.model.User;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.adminapplication.utils.Constants.BALANCE_KEY;
import static com.example.adminapplication.utils.Constants.EMAIL_KEY;
import static com.example.adminapplication.utils.Constants.IMAGE_KEY;
import static com.example.adminapplication.utils.Constants.NAME_KEY;
import static com.example.adminapplication.utils.Constants.PASSWORD_KEY;

public class AllUsersAdaptor extends RecyclerView.Adapter<AllUsersAdaptor.PurchaseVH> {

    private List<User> users=new ArrayList<>();
    private Context context;


    public AllUsersAdaptor(Context context) {
        this.context=context;
        //Log.e("Adaptor","AllUsersAdaptor");
    }

    @NonNull
    @Override
    public PurchaseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_all_users, parent, false);
        return new PurchaseVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseVH holder, final int position) {
        holder.adminName.setText(users.get(position).getName());
        holder.email.setText(users.get(position).getEmail());
        holder.phone.setText(users.get(position).getPhone());
        holder.password.setText(users.get(position).getPassword());

        String balanceV=users.get(position).getBalance()+"";
        holder.balance.setText(balanceV);
       // holder.balance.setText("balance");
        Log.e("allUsers adaptor image","https://onlinesd.store/billing/ImageUploadApi/uploads/registrations/"+users.get(position).getImage());
        Picasso.get().load("https://onlinesd.store/billing/ImageUploadApi/uploads/registrations/"+users.get(position).getImage()).error(R.drawable.ic_error).into(holder.imageView, new Callback() {

            // Picasso.get().load("http://10.0.2.2/photos/"+users.get(position).getImage()).error(R.drawable.ic_error).into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                Log.d("Error : ", e.getMessage());
                holder.progressBar.setVisibility(View.GONE);
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

    private void sendData(User user) {
      Intent intent = new Intent(context, ShowUserActivity.class);

        intent.putExtra(NAME_KEY,user.getName());
        intent.putExtra(EMAIL_KEY,user.getEmail());
        intent.putExtra(IMAGE_KEY,user.getImage());
        intent.putExtra(PASSWORD_KEY,user.getPassword());

        intent.putExtra("PHONE",user.getPhone());
        intent.putExtra(BALANCE_KEY,user.getBalance());

        context.startActivity(intent);
      //  Log.d("Adaptor data","id "+rq.getId()+" userID "+rq.getUserId());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

  static   class PurchaseVH extends RecyclerView.ViewHolder{

        private TextView adminName,email,password,phone,balance;
        private LinearLayout admin_layout;
        private ImageView imageView;
        private ProgressBar progressBar;
        public PurchaseVH(@NonNull View itemView) {
            super(itemView);
            adminName=itemView.findViewById(R.id.userName);
            admin_layout=itemView.findViewById(R.id.adminLin);
            email=itemView.findViewById(R.id.userEmail);
            password=itemView.findViewById(R.id.userPass);
            phone=itemView.findViewById(R.id.userPhone);
            balance=itemView.findViewById(R.id.userBalance);
            imageView=itemView.findViewById(R.id.clint_image);
            progressBar=itemView.findViewById(R.id.clintProgress);
        }
    }

}
