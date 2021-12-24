package com.example.adminapplication.reposatories;


import com.example.adminapplication.model.Result;
import com.example.adminapplication.network.RetrofitClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ImeReqestsREPO {
    Observable<Result> observable;

    public ImeReqestsREPO() {
        observable= RetrofitClient.getInstance()
                .getApiService().getAllImeRequests()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<Result> getObservable() {
        return observable;
    }


}
