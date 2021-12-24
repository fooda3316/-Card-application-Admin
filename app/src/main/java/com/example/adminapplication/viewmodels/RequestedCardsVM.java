package com.example.adminapplication.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.adminapplication.model.Request;
import com.example.adminapplication.model.Results;
import com.example.adminapplication.model.User;
import com.example.adminapplication.reposatories.RequestedCardsREPO;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RequestedCardsVM extends AndroidViewModel {
        private RequestedCardsREPO repository = null;
        public MutableLiveData<List<Request>> requestLiveData;
        private Observer<Results> observer;
        public RequestedCardsVM(@NonNull Application application) {
                super(application);
                requestLiveData = new MutableLiveData<>();

        }

    public  MutableLiveData<List<Request>> getUnfinishedLiveData(){
        repository = new RequestedCardsREPO();
            observer = new Observer<Results>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Results value) {

                        assert value != null;
                            requestLiveData.setValue(value.getRequests());
                    }

                    @Override
                    public void onError(Throwable e) {
                            Log.e("onError sell H",e.toString());
                    }

                    @Override
                    public void onComplete() {
                     //   Log.e("onComplete","onComplete");
                    }
            };

            repository.getObservable().subscribe(observer);
                        Log.e("is getObservable null", String.valueOf(repository.getObservable()==null));

            return requestLiveData;
    }
}
