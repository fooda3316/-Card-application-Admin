package com.example.adminapplication.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.adminapplication.model.Card;
import com.example.adminapplication.model.Cards;
import com.example.adminapplication.reposatories.SubCardRepository;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SubCardViewModel extends AndroidViewModel {
        private SubCardRepository repository = null;
        public MutableLiveData<List<Card>> usersLiveData;
        private Observer<Cards> observer;
        public SubCardViewModel(@NonNull Application application) {
                super(application);
                usersLiveData  = new MutableLiveData<>();

        }

    public MutableLiveData<List<Card>> getCardsLiveData(String title, String branch){
            Log.e("MutableLiveData","title "+title+" branch "+branch);
        repository = new SubCardRepository(title,branch);
                try {


            observer = new Observer<Cards>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("cards onSubscribe",d.toString());

                    }

                    @Override
                    public void onNext(Cards value) {
                        //    Log.e("onNext",value.getCards().get(2).getTitle().toString());
                       usersLiveData.setValue(value.getCards());
                    }

                    @Override
                    public void onError(Throwable e) {
                            Log.e("onErrorObserver",e.toString());


                    }

                    @Override
                    public void onComplete() {
                     //   Log.e("onComplete","onComplete");
                    }
            };}
                catch (Exception e){
                        Log.e("Exception",e.getMessage());
                }
                try {
            repository.getObservable().subscribe(observer);
                        Log.e("is getObservable null", String.valueOf(repository.getObservable()==null));
                }
                 catch (Exception e){
                    Log.e("repository Exception",e.getMessage());
            }
            return usersLiveData;
    }
}
