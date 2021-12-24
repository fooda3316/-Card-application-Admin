package com.example.adminapplication.fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.adminapplication.R;
import com.example.adminapplication.adaptors.ImeAdaptor;
import com.example.adminapplication.api.APIService;
import com.example.adminapplication.model.Ime;
import com.example.adminapplication.model.Result;
import com.example.adminapplication.network.RetrofitClient;
import com.example.adminapplication.viewmodels.ImeVM;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminsFragment extends Fragment {
    private ImeVM imeVM;
    private MaterialDialog mDialog;
    private RecyclerView recyclerView;
    private ImeAdaptor adaptor;
    private APIService apiService;

    public AdminsFragment() {
        // Required empty public constructor
    }

    public static AdminsFragment newInstance() {
        AdminsFragment fragment = new AdminsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiService = RetrofitClient.getInstance().getApiService();
        showLoadingDialog();
        imeVM = ViewModelProviders.of(this).get(ImeVM.class);
        recyclerView = view.findViewById(R.id.imeRV);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptor = new ImeAdaptor();
        recyclerView.setAdapter(adaptor);
        deleteSwipe(recyclerView);
        imeVM.getImeLiveData().observe(this.getViewLifecycleOwner(), new Observer<List<Ime>>() {
            @Override
            public void onChanged(List<Ime> list) {
                adaptor.setList(list);
                dismissLoadingDialog();

            }
        });

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admins, container, false);
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

    private void deleteSwipe(RecyclerView recyclerView) {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                adaptor.notifyDataSetChanged();
                int poss = viewHolder.getLayoutPosition();
                String name = adaptor.getImeAt(poss);
                showAlert(name);
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void deleteIme(String name) {
        showLoadingDialog();
        Call<Result> call = apiService.deleteIme(name);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (!response.isSuccessful()) {
                    Log.e("error code", "Code: " + response.code());
                    return;
                }
                if (!response.body().getError()) {
                    dismissLoadingDialog();
                    getActivity().recreate();
                    Toast.makeText(getContext(), "تم الحذف", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("AdminFailure", t.toString());
                dismissLoadingDialog();
            }
        });
    }
    public void showAlert(String name) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert
                .setIcon(android.R.drawable.stat_notify_error)
                .setTitle("مسح")
                .setMessage(" هل ترغب فعلاً في مسح " + name)
                .setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteIme(name);
                        //do some thing
                    }
                }).show();
    }
}