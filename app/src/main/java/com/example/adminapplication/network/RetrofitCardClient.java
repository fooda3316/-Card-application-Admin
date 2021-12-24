package com.example.adminapplication.network;



import com.example.adminapplication.api.APIService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static com.example.adminapplication.api.APIUrl.BASE_URL;
import static com.example.adminapplication.api.APIUrl.CARD_BASE_URL;


public class RetrofitCardClient {

    private static RetrofitCardClient mInstance;
    private Retrofit retrofit;

    Gson nGson = new GsonBuilder()
            .setLenient()
            .create();
    Gson gson = new GsonBuilder().serializeNulls().create();

    private RetrofitCardClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(CARD_BASE_URL)
               .addConverterFactory(LenientGsonConverterFactory.create(nGson))
               // .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(RestClient.getClient())
                .build();
    }

    public static synchronized RetrofitCardClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitCardClient();
        }
        return mInstance;
    }

    public APIService getApiService() {
        return retrofit.create(APIService.class);
    }



}

