package com.example.adminapplication.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.adminapplication.model.Admin;
import com.example.adminapplication.model.Results;
import com.example.adminapplication.model.UnfinishedRQ;
import com.example.adminapplication.reposatories.AdminsREPO;
import com.example.adminapplication.reposatories.UnfinishedRQREPO;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class AdmisVM extends AndroidViewModel {
        private AdminsREPO repository = null;
        public MutableLiveData<List<Admin>> usersLiveData;
        private Observer<Results> observer;
        public AdmisVM(@NonNull Application application) {
                super(application);
                usersLiveData  = new MutableLiveData<>();

        }

    public  MutableLiveData<List<Admin>> getUnfinishedLiveData(){
        repository = new AdminsREPO();
            observer = new Observer<Results>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("cards onSubscribe",d.toString());

                    }

                    @Override
                    public void onNext(Results value) {

                        Log.e("wallet error", String.valueOf(value==null));
                        assert value != null;
                        Log.e("onNext SN", value.toString());
                        Log.e("name onNext", String.valueOf(value.getAdmins().get(0).getName()));
                      usersLiveData.setValue(value.getAdmins());
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

            return usersLiveData;
    }
}
