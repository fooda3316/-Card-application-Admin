package com.example.adminapplication.adaptors;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.adminapplication.R;
import com.example.adminapplication.activities.reqests.UnfinishedRQShowActivity;
import com.example.adminapplication.model.UnfinishedRQ;

import java.util.ArrayList;
import java.util.List;

public class UnfinishedRQAdaptor extends RecyclerView.Adapter<UnfinishedRQAdaptor.PurchaseVH> {

    private List<UnfinishedRQ> sellHistories=new ArrayList<>();
    private Context context;


    public UnfinishedRQAdaptor(Context context) {
        this.context=context;

    }

    @NonNull
    @Override
    public PurchaseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_unfinished_rq, parent, false);
        return new PurchaseVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseVH holder, final int position) {
        holder.adminTv.setText(sellHistories.get(position).getName());
        holder.admin_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData( sellHistories.get(position));
            }
        });
       // holder.admin_layout.setBackgroundResource(backgrounds[holder.backgroundIndex]);

        //   Log.e("balance",sellHistories.get(position).getBalance()+"");
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            saveClip(sellHistories.get(position).getOperation());
            }
        });
  }
    private void saveClip(String text ){
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, "تم النسخ الى الحافظة", Toast.LENGTH_SHORT).show();
    }
    private void sendData(UnfinishedRQ rq) {
      Intent intent = new Intent(context, UnfinishedRQShowActivity.class);
        intent.putExtra("id",rq.getId());
        intent.putExtra("userID",rq.getUserId());
        intent.putExtra("name",rq.getName());
        intent.putExtra("image",rq.getImage());
        context.startActivity(intent);
      //  Log.d("Adaptor data","id "+rq.getId()+" userID "+rq.getUserId());
    }

    @Override
    public int getItemCount() {
        return sellHistories.size();
    }
    public void setUnfinishedRQ(List<UnfinishedRQ> walletInfos) {
        this.sellHistories = walletInfos;
        notifyDataSetChanged();
    }

  static   class PurchaseVH extends RecyclerView.ViewHolder{

        private TextView adminTv;
        private LinearLayout admin_layout;
        private ImageButton imageButton;
        public PurchaseVH(@NonNull View itemView) {
            super(itemView);
            adminTv=itemView.findViewById(R.id.admonTv);
            admin_layout=itemView.findViewById(R.id.row_admin_layout);
           imageButton =itemView.findViewById(R.id.imgSave);
        }
    }

}
