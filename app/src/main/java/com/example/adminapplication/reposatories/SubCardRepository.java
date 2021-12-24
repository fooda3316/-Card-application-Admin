package com.example.adminapplication.reposatories;






import com.example.adminapplication.model.Cards;
import com.example.adminapplication.network.RetrofitClient;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SubCardRepository {
    Observable<Cards> observable;

    public SubCardRepository(String title, String branch) {
        observable= RetrofitClient.getInstance()
                .getApiService().getSubCards(title,branch)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


    }


    public Observable<Cards> getObservable() {
      //  Log.d("is null", String.valueOf(observable==null));
        if (observable==null)
            return new Observable<Cards>() {
                @Override
                protected void subscribeActual(Observer<? super Cards> observer) {

                }
            };
        return observable;
    }
}
