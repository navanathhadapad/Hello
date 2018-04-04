package com.tagcor.tagcorproject.Servers;

import com.tagcor.tagcorproject.B2B.B2b_model;
import com.tagcor.tagcorproject.B2B.B2b_proModel;
import com.tagcor.tagcorproject.B2B.B2b_scatModel;
import com.tagcor.tagcorproject.B2B.B2b_sscatModel;
import com.tagcor.tagcorproject.C2C.C2cMain_model;
import com.tagcor.tagcorproject.C2C.C2cSub_model;
import com.tagcor.tagcorproject.Models.ImageModul;
import com.tagcor.tagcorproject.Models.LoginModel;
import com.tagcor.tagcorproject.Models.ProfileModel;
import com.tagcor.tagcorproject.TJobs.Jobs_Model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Navanath on 3/13/2018.
 */
public interface ServiceOperations {
    @FormUrlEncoded
    @POST("register/login")
    Call<LoginModel> login(@Field("email") String email, @Field("upassword") String paswwrod);

    @GET("Personal_api/user/{userId}")
    Call<ProfileModel> getUserProfile(@Path("userId") String userId);

    @GET("personal_api/navanath/") Call<ImageModul> getImageData();

    /*----------------------B2B apps links Start here------------------------------*/

    @GET("Personal_api/b2b") Call<B2b_model> getProDetails();

    @GET("Personal_api/scat/{sid}")
    Call<B2b_scatModel> getSubcatPro(@Path("sid") String sid);

    @GET("Personal_api/sscat/{ssid}")
    Call<B2b_sscatModel> getSubsubPro(@Path("ssid") String ssid);

    @GET("Personal_api/product/{proid}")
    Call<B2b_proModel> getProducts(@Path("proid") String proid);

    /*----------------------B2B apps links End here------------------------------*/

    /*----------------------C2C apps links Start here------------------------------*/

    @GET("Personal_api/c2c") Call<C2cMain_model> getc2cProDetails();

    @GET("Personal_api/c2c_sub/{s_id}")
    Call<C2cSub_model> getSubCat(@Path("s_id") String s_id);

    /*----------------------C2C apps links End here------------------------------*/

    //jobs url start here

    @GET("Personal_api/homepage_jobs") Call<Jobs_Model> getJobsDetails();

}
