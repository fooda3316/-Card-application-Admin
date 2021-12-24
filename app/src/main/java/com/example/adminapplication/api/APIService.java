package com.example.adminapplication.api;



import com.example.adminapplication.model.Cards;
import com.example.adminapplication.model.Pages;
import com.example.adminapplication.model.Result;
import com.example.adminapplication.model.Results;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Fooda on 14/04/17.
 */

public interface APIService {



    @FormUrlEncoded
    @POST("saveChargeRequest")
    Call<Result> saveChargeRequest(
            @Field("userId") int userId,
            @Field("image") String image
    );


    @GET("getUnfinishedRequests")
    Observable<Results> getUnfinishedRequests(

    );
    @GET("getAllAdmins")
    Observable<Results> getAllAdmins(

    );
    @GET("requestedCards")
    Observable<Results> getRequestedCards(

    );
    @GET("getAllUsers")
    Observable<Results> getAllUsers(

    );
    @GET("getAllUsers")
    Call<Results> getAllUsersCall(

    );
    @GET("getAllCards")
    Observable<Results> getAllCards(

    );
    @GET("getAllAdminCards")
    Observable<Results> getAllAdminCards(

    );
    @FormUrlEncoded
    @POST("getAllAdminCardsNew")
    Call<Results> getAllAdminCardsNew(
            @Field("mainName") String  mainName,
            @Field("branch") String  branch,
            @Field("name") String  name

    );
    @FormUrlEncoded
    @POST("subCards")
    Observable<Cards> getSubCards(@Field("name") String name,
                                  @Field("branch") String branch
    );
    @FormUrlEncoded
    @PUT("updateBalance")
    Call<Result> updateBalance(
            @Field("balance") int balance,
            @Field("id") int id,
            @Field("requestID") int requestID,
            @Field("adminName") String adminName,
            @Field("ime") String ime,
            @Field("date") String date
    );
    @GET("getVerificationNumber")
    Call<Result> getVerificationNumber();
    //getting messages
    @GET("managerKey/{ime}")
    Call<Result> getManagerKeyIme(@Path("ime") String ime);

    @FormUrlEncoded
    @POST("getAdminIme")
    Call<Result> getAdminIme(
            @Field("ime") String ime
    );
    @FormUrlEncoded
    @POST("deleteAdmin")
    Call<Result> deleteAdmin(
            @Field("id") int id);
    @FormUrlEncoded
    @POST("deletePage")
    Call<Result> deletePage(
            @Field("name") String name);
    @FormUrlEncoded
    @POST("deleteIme")
    Call<Result> deleteIme(
            @Field("ime") String ime);
    @FormUrlEncoded
    @POST("deleteRequest")
    Call<Result> deleteRequest(
            @Field("id") int id);
    @FormUrlEncoded
    @POST("addAdminCards")
    Call<Result> addAdminCards(
            @Field("name") String title,
            @Field("subName") String subName,
            @Field("branch") String branch,
            @Field("serialNumber") String serialNumber);
    @FormUrlEncoded
    @POST("addIme")
    Call<Result> addIme(
            @Field("ime") String ime,
    @Field("name") String name);
    @FormUrlEncoded
    @POST("addAdminIme")
    Call<Result> addAdminIme(
            @Field("ime") String ime,
            @Field("name") String name);
    @FormUrlEncoded
    @PUT("updateCardValue")
    Call<Result> updateCardValue(
            @Field("image") String image,
            @Field("ime") String ime,
            @Field("value") int value);
    @Multipart
    @POST("uploadCardText.php")
    Call<Result> uploadCard(
            @Part("cardPhoto\"; filename=\"myfile.jpg\" ") RequestBody file,
            @Part("categoryId") RequestBody categoryId,
            @Part("imageName") RequestBody imageName,
            @Part("price") RequestBody price);
    @Multipart
    @POST("uploadPage.php")
    Call<Result> uploadPage(
            @Part("pageImage\"; filename=\"myfile.jpg\" ") RequestBody file,
            @Part("name") RequestBody name,
            @Part("uri") RequestBody uri);
    @Multipart
    @POST("uploadImageText.php")
    Call<Result> uploadPicture(
            @Part("userPhoto\"; filename=\"myfile.jpg\" ") RequestBody file,
            @Part("uri") RequestBody uri,
            @Part("imageName") RequestBody imageName);

    @GET("imes")
    Observable<Result> getAllImes();
    @GET("getAllImeRequests")
    Observable<Result> getAllImeRequests();
    @GET("pages")
    Observable<Pages> getAllPages();
    @GET("getBranches/{name}")
    Call<Results> getBranch(
            @Path("name") String name
    );
}
