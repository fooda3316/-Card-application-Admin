package com.example.adminapplication.adaptors;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.adminapplication.R;
import com.example.adminapplication.api.APIService;
import com.example.adminapplication.model.Ime;
import com.example.adminapplication.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

public class ImeRequestsAdaptor extends RecyclerView.Adapter<ImeRequestsAdaptor.PurchaseVH> {

    private List<Ime> list =new ArrayList<>();
    private Activity activity;

    private APIService apiService;
    private MaterialDialog mDialog;
    public ImeRequestsAdaptor() {
        apiService= RetrofitClient.getInstance().getApiService();

    }

    @NonNull
    @Override
    public PurchaseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_ime, parent, false);
        return new PurchaseVH(itemView);
    }
public Ime getImeAtPos(int pos){
        return list.get(pos);
}
    @Override
    public void onBindViewHolder(@NonNull PurchaseVH holder, final int position) {
        holder.adminTv.setText(list.get(position).getIme());
        holder.imeName.setText(list.get(position).getName());
        holder.adminTv.setVisibility(View.GONE);


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
    public String getDeviceIMEI() {
        String deviceUniqueIdentifier = null;
        TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != tm) {
            deviceUniqueIdentifier = tm.getDeviceId();
        }
        if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
            deviceUniqueIdentifier = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceUniqueIdentifier;
    }




}
