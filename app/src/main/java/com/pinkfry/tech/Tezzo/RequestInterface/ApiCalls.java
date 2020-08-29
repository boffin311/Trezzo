package com.pinkfry.tech.Tezzo.RequestInterface;

import com.pinkfry.tech.Tezzo.Model.AttendanceModel.AttendanceModel;
import com.pinkfry.tech.Tezzo.Model.DietModel.DietResponse;
import com.pinkfry.tech.Tezzo.Model.AuthModel.LoginModel;
import com.pinkfry.tech.Tezzo.Model.FeeModel.PaymentAddedModel;
import com.pinkfry.tech.Tezzo.Model.NoticeModel.NoticeModel;
import com.pinkfry.tech.Tezzo.Model.PlanModel.PlanResponse;
import com.pinkfry.tech.Tezzo.Model.WorkOutModel.WorkOutModel;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiCalls {
    @FormUrlEncoded
    @POST("member-login")
    Call<LoginModel> getLoginData(@Field("mobile_no") String mobile_no,@Field("gym_no")String gym_no,@Field("password")String password);
    @FormUrlEncoded
    @POST("fetch-diet-member")
    Call<DietResponse> getDietData(@Field("member_id") String member_id);
    @FormUrlEncoded
    @POST("add-attendance")
    Call<AttendanceModel> getAttendanceResult(@Field("member_id")String member_id,@Field("gymId")String gymId);
    @FormUrlEncoded
    @POST("all-notice")
    Call<ArrayList<NoticeModel>> getNoticeData(@Field("gymId")String gymId);
    @FormUrlEncoded
    @POST("fetch-workout-member")
    Call<WorkOutModel> getWorkOut(@Field("member_id")String member_id);
    @FormUrlEncoded
    @POST("get-plan")
    Call<PlanResponse> getPlans(@Field("gymId")String gymId);
    @FormUrlEncoded
    @POST("add-fee")
    Call<PaymentAddedModel> getPaymentAddStatus(@Field("gymId")String gymId, @Field("for_next")String forNext,
                                                @Field("user_id")String userId, @Field("payment_obj") JSONObject paymentObj);

}
