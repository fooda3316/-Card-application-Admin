package com.example.adminapplication.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.adminapplication.model.Admin;
import com.example.adminapplication.model.Ime;
import com.example.adminapplication.model.Result;
import com.example.adminapplication.model.Results;
import com.example.adminapplication.reposatories.AdminsREPO;
import com.example.adminapplication.reposatories.ImeREPO;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ImeVM extends AndroidViewModel {
        private ImeREPO repository = null;
        public MutableLiveData<List<Ime>> imeLiveData;
        private Observer<Result> observer;
        public ImeVM(@NonNull Application application) {
                super(application);
                imeLiveData = new MutableLiveData<>();

        }

    public  MutableLiveData<List<Ime>> getImeLiveData(){
        repository = new ImeREPO();
            observer = new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result value) {

                        imeLiveData.setValue(value.getImes());
                    }

                    @Override
                    public void onError(Throwable e) {
                            Log.e("onError Ime",e.toString());
                    }

                    @Override
                    public void onComplete() {
                     //   Log.e("onComplete","onComplete");
                    }
            };

            repository.getObservable().subscribe(observer);

            return imeLiveData;
    }
}
