package com.example.adminapplication.reposatories;


import com.example.adminapplication.model.Results;
import com.example.adminapplication.network.RetrofitClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class RequestedCardsREPO {
    Observable<Results> observable;

    public RequestedCardsREPO() {
        observable= RetrofitClient.getInstance()
                .getApiService().getRequestedCards()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<Results> getObservable() {
        return observable;
    }


}
