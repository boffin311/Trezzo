package com.pinkfry.tech.Tezzo.RequestInterface;

import com.pinkfry.tech.Tezzo.Model.LoginModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiCalls {
    @FormUrlEncoded
    @POST("member-login")
    Call<LoginModel> getLoginData(@Field("mobile_no") String mobile_no,@Field("gym_no")String gym_no);

}
