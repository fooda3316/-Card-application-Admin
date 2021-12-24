package com.example.adminapplication.network;



import com.example.adminapplication.api.APIService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.adminapplication.api.APIUrl.BASE_URL;


public class RetrofitClient {

    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    Gson nGson = new GsonBuilder()
            .setLenient()
            .create();
    Gson gson = new GsonBuilder().serializeNulls().create();

    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
               .addConverterFactory(LenientGsonConverterFactory.create(nGson))
               // .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
               .client(RestClient.getClient())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public APIService getApiService() {
        return retrofit.create(APIService.class);
    }



}

