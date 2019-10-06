package com.enogy.detectedface.model.retrofit;

import com.enogy.detectedface.utils.Config;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCreated {
    public static retrofit2.Retrofit retrofit;

    public static retrofit2.Retrofit createRetrofit(String BASE_API) {
//        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(50, TimeUnit.SECONDS)
//                .writeTimeout(50, TimeUnit.SECONDS)
//                .readTimeout(50, TimeUnit.SECONDS)
//                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
//                    .client(okHttpClient)
                    .baseUrl(BASE_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;

    }

    public static Api createApi(String BASE_API) {
        return createRetrofit(BASE_API).create(Api.class);
    }
}
