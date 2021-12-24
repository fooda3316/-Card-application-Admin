package com.example.adminapplication.adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminapplication.R;
import com.example.adminapplication.activities.reqests.RequestShowActivity;
import com.example.adminapplication.model.Request;

import java.util.ArrayList;
import java.util.List;

public class RequestAdaptor extends RecyclerView.Adapter<RequestAdaptor.PurchaseVH> {

    private List<Request> requestList =new ArrayList<>();
    private Context context;


    public RequestAdaptor(Context context) {
        this.context=context;

    }

    @NonNull
    @Override
    public PurchaseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_requests, parent, false);
        return new PurchaseVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseVH holder, final int position) {
        String name =requestList.get(position).getSubName()+" "+requestList.get(position).getTitle()+" "+requestList.get(position).getBranch();
        holder.crdName.setText(name);
        holder.date.setText(requestList.get(position).getDate());
        holder.admin_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendData( requestList.get(position));
            }
        });

     }

    private void sendData(Request rq) {
      Intent intent = new Intent(context, RequestShowActivity.class);
        intent.putExtra("SubName",rq.getSubName());
        intent.putExtra("Title",rq.getTitle());
        intent.putExtra("Branch",rq.getBranch());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }
    public void setRequests(List<Request> requests) {
        this.requestList = requests;
        notifyDataSetChanged();
    }

  static   class PurchaseVH extends RecyclerView.ViewHolder{

        private TextView crdName,date;
        private LinearLayout admin_layout;
        public PurchaseVH(@NonNull View itemView) {
            super(itemView);
            crdName =itemView.findViewById(R.id.rName);
            date=itemView.findViewById(R.id.rDate);
            admin_layout=itemView.findViewById(R.id.request_layout);
        }
    }
    public Request getRequestAt(int position) {
        return requestList.get(position);
    }
}
