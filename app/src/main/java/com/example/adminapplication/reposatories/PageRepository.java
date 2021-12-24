package com.example.adminapplication.reposatories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import com.example.adminapplication.model.Page;
import com.example.adminapplication.model.Pages;
import com.example.adminapplication.network.RetrofitClient;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PageRepository {
    public MutableLiveData<List<Page>> cardsLiveData;
    Observable<Pages> observable;

    public PageRepository() {
        cardsLiveData  = new MutableLiveData<>();
        try {


        observable= RetrofitClient.getInstance()
                .getApiService().getAllPages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        }
        catch (Exception ex){
            Log.e("observableException",ex.toString());
        }

    }


    public Observable<Pages> getObservable() {
      //  Log.d("is null", String.valueOf(observable==null));
        if (observable==null)
            return new Observable<Pages>() {
                @Override
                protected void subscribeActual(Observer<? super Pages> observer) {

                }
            };
        return observable;
    }
}
