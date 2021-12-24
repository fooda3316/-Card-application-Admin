package com.example.adminapplication.reposatories;


import com.example.adminapplication.model.Results;
import com.example.adminapplication.network.RetrofitClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class AdminsREPO {
    Observable<Results> observable;

    public AdminsREPO() {
        observable= RetrofitClient.getInstance()
                .getApiService().getAllAdmins()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<Results> getObservable() {
        return observable;
    }


}
