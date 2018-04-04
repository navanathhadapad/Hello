package com.tagcor.tagcorproject.net;

import com.tagcor.tagcorproject.Servers.ServiceOperations;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Navanath on 3/13/2018.
 */

public class RetrofitAdapter {
   private static final String BASE_URL = "http://192.168.0.100:80/Tagcor/";


    public static Retrofit getRetrofitInstance() {

        /* return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();*/
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder().addHeader("authorization", "Basic YWRtaW46MTIzNA==").build();
                return chain.proceed(request);
            }
        });
        return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL).client(httpClient.build()).build();

    }

    public static ServiceOperations getBaseClassService() {
        return getRetrofitInstance().create(ServiceOperations.class);
    }
}
