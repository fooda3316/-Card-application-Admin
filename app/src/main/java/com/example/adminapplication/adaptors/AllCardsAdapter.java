package com.example.adminapplication.adaptors;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.adminapplication.R;
import com.example.adminapplication.activities.cards.SubCardsActivity;
import com.example.adminapplication.activities.cards.SubCardsShowActivity;
import com.example.adminapplication.api.APIService;
import com.example.adminapplication.model.Card;
import com.example.adminapplication.model.Home;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.example.adminapplication.utils.Constants.CARD_BRANCH;
import static com.example.adminapplication.utils.Constants.CARD_ID;
import static com.example.adminapplication.utils.Constants.CARD_TITLE;
import static com.example.adminapplication.utils.Constants.CARD_TYPE;

public class AllCardsAdapter extends RecyclerView.Adapter<AllCardsAdapter.MyViewHolder> {

    List<Card> cardList;
    Context context;
    private MaterialDialog mDialog;
    private APIService aPIService;

    public AllCardsAdapter(List<Card> cardList, Context context) {
        this.cardList = cardList;
        this.context = context;



    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView;

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_all_cards, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final Card card = cardList.get(position);

       // holder.progressBar.setVisibility(View.GONE);
        String imageUri="http://onlinesd.store/billing/ImageUploadApi/uploads/cards/"+card.getImage();
        Glide.with(context).load(imageUri).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                Log.e("GlideException : ", e.getMessage());
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                //if you want to convert the drawable to ImageView
                Bitmap bitmapImage  = ((BitmapDrawable) resource).getBitmap();
                holder.progressBar.setVisibility(View.GONE);


                return false;
            }
        }).into(holder.imageView);

        holder.products_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSubCardsActivity(cardList.get(position));

            }
        });

    }

    private void goToSubCardsActivity(Card card) {
        Intent intent=(new Intent(context, SubCardsShowActivity.class));
        intent.putExtra(CARD_ID,card.getId());
        intent.putExtra(CARD_TYPE,card.getSubName());
        intent.putExtra(CARD_BRANCH,card.getBranch());
        intent.putExtra(CARD_TITLE,card.getTitle());
        context.startActivity(intent);
    }


    @Override
    public int getItemCount() {

        return cardList.size();

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
            progressBar = itemView.findViewById(R.id.all_product_progressbar);
            products_layout=itemView.findViewById(R.id.row_products_layout);

        }
    }



}
