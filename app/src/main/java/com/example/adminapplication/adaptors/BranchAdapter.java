package com.example.adminapplication.adaptors;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.adminapplication.R;
import com.example.adminapplication.activities.cards.SubCardsActivity;
import com.example.adminapplication.model.Branch;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.adminapplication.utils.Constants.BRANCH_KEY;
import static com.example.adminapplication.utils.Constants.BRANCH_TITLE_KEY;


public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.MyViewHolder> {

    List<Branch> branches;
    Context context;
    private String title;

    public BranchAdapter(List<Branch> branches, Context context, String title) {
        this.branches = branches;
        this.context = context;
        this.title = title;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_branch, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
      //  Log.e("image", "https://onlinesd.store/billing/images/branch/" + branches.get(position).getImage());
        Picasso.get().load("https://onlinesd.store/billing/images/branch/" + branches.get(position).getImage()).error(R.drawable.no_image).into(holder.imageView, new Callback() {
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

//        Picasso.get().load(branches.get(position).getSrc()).error(R.drawable.no_image).into(holder.imageView);
        holder.products_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, SubCardsActivity.class).putExtra(BRANCH_KEY, branches.get(position).getBranch()).putExtra(BRANCH_TITLE_KEY, title));
            }
        });

    }

    @Override
    public int getItemCount() {
        return branches.size();

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView progressBar;
        private LinearLayout products_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.all_product_image);
            products_layout = itemView.findViewById(R.id.row_products_layout);
            progressBar = itemView.findViewById(R.id.branch_progressbar);

        }
    }

}
