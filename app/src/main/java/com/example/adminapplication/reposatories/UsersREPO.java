package com.example.adminapplication.reposatories;


import com.example.adminapplication.model.Results;
import com.example.adminapplication.network.RetrofitClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class UsersREPO {
    Observable<Results> observable;

    public UsersREPO() {
        observable= RetrofitClient.getInstance()
                .getApiService().getAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<Results> getObservable() {
        return observable;
    }


}
