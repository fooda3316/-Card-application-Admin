package com.example.adminapplication.reposatories;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.example.adminapplication.model.Results;
import com.example.adminapplication.network.RetrofitClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;



public class UnfinishedRQREPO {
    Observable<Results> observable;

    public UnfinishedRQREPO() {
        observable= RetrofitClient.getInstance()
                .getApiService().getUnfinishedRequests()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<Results> getObservable() {
        return observable;
    }


}
