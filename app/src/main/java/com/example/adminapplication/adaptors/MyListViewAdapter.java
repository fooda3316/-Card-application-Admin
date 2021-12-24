package com.example.adminapplication.adaptors;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.adminapplication.R;
import com.example.adminapplication.model.User;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Contact list adapter
 * Created by leosunzh on 2015/12/12.
 */
public class MyListViewAdapter extends ArrayAdapter<User> {
    public int  resourceId;//LayoutID
    //MyResolver myResolver;

    public MyListViewAdapter(Context context, int resource, List<User> list) {
        super(context, resource,list);
        this.resourceId = resource;
       // myResolver = new MyResolver(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);
        ViewHolder viewHolder;
        View view;

        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.name_item = view.findViewById(R.id.name_item);
            viewHolder.number_item =  view.findViewById(R.id.number_item);
            viewHolder.photo_item =  view.findViewById(R.id.photo_item);
            viewHolder.progressBar =view.findViewById(R.id.showUserProgress);


            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.name_item.setText(user.getName());
        viewHolder.number_item.setText(user.getEmail());
        Picasso.get().load("https://onlinesd.store/billing/ImageUploadApi/uploads/registrations/"+user.getImage()).error(R.drawable.ic_error).into(viewHolder.photo_item, new Callback() {
            @Override
            public void onSuccess() {
                viewHolder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

                Log.d("Error : ", e.getMessage());
                viewHolder.progressBar.setVisibility(View.GONE);
            }
        });

        return view;
    }

//    @Override
//    public int getItemViewType(int position) {
//        switch (position){
//            case 0:re
//        }
//    }

    class ViewHolder{
        ImageView photo_item;
        TextView name_item,number_item;
        ProgressBar progressBar;
    }

}
