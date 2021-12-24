package com.example.adminapplication.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.adminapplication.R;
import com.example.adminapplication.adaptors.ImeAdaptor;
import com.example.adminapplication.adaptors.ImeRequestsAdaptor;
import com.example.adminapplication.api.APIService;
import com.example.adminapplication.model.Ime;
import com.example.adminapplication.model.Result;
import com.example.adminapplication.network.RetrofitClient;
import com.example.adminapplication.viewmodels.ImeRqestsVM;
import com.example.adminapplication.viewmodels.ImeVM;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminsRequestsFragment extends Fragment {
    private ImeRqestsVM imeRqestsVM;
    private MaterialDialog mDialog;
    private RecyclerView recyclerView;
    private ImeRequestsAdaptor adaptor;
    private APIService apiService;
    public AdminsRequestsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AdminsRequestsFragment newInstance() {
        AdminsRequestsFragment fragment = new AdminsRequestsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admins_requests, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiService = RetrofitClient.getInstance().getApiService();
        showLoadingDialog();
        imeRqestsVM = ViewModelProviders.of(this).get(ImeRqestsVM.class);
        recyclerView = view.findViewById(R.id.imeRqRV);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptor = new ImeRequestsAdaptor();
        recyclerView.setAdapter(adaptor);
        deleteSwipe(recyclerView);
        imeRqestsVM.getImeLiveData().observe(this.getViewLifecycleOwner(), new Observer<List<Ime>>() {
            @Override
            public void onChanged(List<Ime> list) {
                adaptor.setList(list);
                dismissLoadingDialog();

            }
        });
    }

    public void showLoadingDialog() {
        mDialog = new MaterialDialog.Builder(getContext())
                .title(R.string.app_name)
                .content(R.string.progress_wait)
                .progress(true, 0)
                .show();
    }

    public void dismissLoadingDialog() {
        mDialog.dismiss();
    }
    private void addIme(Ime ime) {
        showLoadingDialog();
        apiService= RetrofitClient.getInstance().getApiService();
        // Log.e("card data","name  "+card.getTitle()+" SubName "+card.getSubName()+" Serialnumber "+card.getSerialnumber()+" value "+card.getValue());
        Call<Result> call=apiService.addIme(ime.getIme(),ime.getName());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (!response.isSuccessful()) {
                    Log.e("error code","Code: " + response.code());
                    Toast.makeText(getContext(), "لم تتم إضافة الرقم بنجاح", Toast.LENGTH_LONG).show();
                    return;
                }
                assert response.body() != null;
                if(!response.body().getError()) {
                    dismissLoadingDialog();
                    Log.d("Message", response.body().getMessage());
                    Toast.makeText(getContext(), "تم إضافة الرقم بنجاح", Toast.LENGTH_LONG).show();
                    getActivity().recreate();
                }
                else {
                    dismissLoadingDialog();
                    Toast.makeText(getContext(), "لم تتم إضافة الرقم بنجاح", Toast.LENGTH_LONG).show();
                    vibrate();

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("Add admin card error",t.toString());
                dismissLoadingDialog();
                Toast.makeText(getContext(), "لم تتم إضافة البطاقة بنجاح", Toast.LENGTH_LONG).show();
                vibrate();
            }
        });
    }

    public void vibrate() {
        Vibrator vibs = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibs.vibrate(200);
    }
    private void deleteSwipe(RecyclerView recyclerView) {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    addIme(adaptor.getImeAtPos(viewHolder.getLayoutPosition()) );
                adaptor.notifyDataSetChanged();

            }
        }).attachToRecyclerView(recyclerView);
    }
}