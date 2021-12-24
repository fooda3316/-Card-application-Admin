package com.example.adminapplication.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.adminapplication.model.Admin;
import com.example.adminapplication.model.Results;
import com.example.adminapplication.model.User;
import com.example.adminapplication.reposatories.AdminsREPO;
import com.example.adminapplication.reposatories.UsersREPO;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class UsersVM extends AndroidViewModel {
        private UsersREPO repository = null;
        public MutableLiveData<List<User>> usersLiveData;
        private Observer<Results> observer;
        public UsersVM(@NonNull Application application) {
                super(application);
                usersLiveData  = new MutableLiveData<>();

        }

    public  MutableLiveData<List<User>> getUnfinishedLiveData(){
        repository = new UsersREPO();
            observer = new Observer<Results>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("cards onSubscribe",d.toString());

                    }

                    @Override
                    public void onNext(Results value) {

                      //  Log.e("wallet error", String.valueOf(value==null));
                        assert value != null;
                       // Log.e("onNext SN", value.toString());
                       // Log.e("name onNext", String.valueOf(value.getUsers().get(0).getName()));
                      usersLiveData.setValue(value.getUsers());
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
