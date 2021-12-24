package com.example.adminapplication.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.example.adminapplication.model.Page;
import com.example.adminapplication.model.Pages;
import com.example.adminapplication.reposatories.PageRepository;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class PageViewModel extends AndroidViewModel {
        private PageRepository repository = null;
        public MutableLiveData<List<Page>> usersLiveData;
        private Observer<Pages> observer;
        public PageViewModel(@NonNull Application application) {
                super(application);
                usersLiveData  = new MutableLiveData<>();

        }

    public MutableLiveData<List<Page>> getCardsLiveData(){
        repository = new PageRepository();
                try {


            observer = new Observer<Pages>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("cards onSubscribe",d.toString());

                    }

                    @Override
                    public void onNext(Pages value) {
                        //    Log.e("onNext",value.getCards().get(2).getTitle().toString());
                      if (null!=value){usersLiveData.setValue(value.getPages());}
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
