package com.example.thithu.API;

import com.example.thithu.Model.SinhVienModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {

    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    APIService MY_API = new Retrofit.Builder()
            .baseUrl("http://192.168.0.112:8080/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);

    @GET("student")
    Call<List<SinhVienModel>> getData();

    @POST("addstudent")
    Call<SinhVienModel> createUser(@Body SinhVienModel model);

    @PUT("edit/{id}")
    Call<SinhVienModel> update(@Path("id") String id, @Body SinhVienModel model);


    @DELETE("/{id}")
    Call<Void> deleteUser(@Path("id") String userId);

}
