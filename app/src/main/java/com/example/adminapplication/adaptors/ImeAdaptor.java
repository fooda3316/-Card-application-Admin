package com.example.adminapplication.adaptors;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminapplication.R;
import com.example.adminapplication.model.Ime;
import com.example.adminapplication.model.Page;

import java.util.ArrayList;
import java.util.List;

public class ImeAdaptor extends RecyclerView.Adapter<ImeAdaptor.PurchaseVH> {

    private List<Ime> list =new ArrayList<>();
    public ImeAdaptor() {

    }

    @NonNull
    @Override
    public PurchaseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_ime, parent, false);
        return new PurchaseVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseVH holder, final int position) {
        holder.adminTv.setText(list.get(position).getIme());
        holder.imeName.setText("Name:"+list.get(position).getName());

  }

    public String getImeAt(int position) {
        //Log.e("position","position is "+position);
        return list.get(position).getIme();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void setList(List<Ime> stringList) {
        this.list = stringList;
        notifyDataSetChanged();
    }

  static   class PurchaseVH extends RecyclerView.ViewHolder{

        private TextView adminTv,imeName;
        public PurchaseVH(@NonNull View itemView) {
            super(itemView);
            adminTv=itemView.findViewById(R.id.admonTv);
            imeName=itemView.findViewById(R.id.imeName);

        }
    }

}
