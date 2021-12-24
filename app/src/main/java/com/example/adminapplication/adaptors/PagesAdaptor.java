package com.example.adminapplication.adaptors;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminapplication.R;
import com.example.adminapplication.model.Page;

import java.util.ArrayList;
import java.util.List;

public class PagesAdaptor extends RecyclerView.Adapter<PagesAdaptor.PurchaseVH> {

    private List<Page> pages =new ArrayList<>();
    private List<String> list =new ArrayList<>();
    public PagesAdaptor() {

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
        holder.adminTv.setText(pages.get(position).getName());

  }

    public Page getPageAt(int position) {
        Log.e("position","position is "+position);
        return pages.get(position);
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }
    public void setPages(List<Page> walletInfos) {
        this.pages = walletInfos;
        notifyDataSetChanged();
    }

  static   class PurchaseVH extends RecyclerView.ViewHolder{

        private TextView adminTv;
        public PurchaseVH(@NonNull View itemView) {
            super(itemView);
            adminTv=itemView.findViewById(R.id.admonTv);
        }
    }

}
