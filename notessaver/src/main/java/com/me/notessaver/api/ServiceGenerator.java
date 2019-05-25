package com.me.notessaver.api;

import android.content.Context;
import android.text.TextUtils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.me.notessaver.config.UrlConfig;
import com.me.notessaver.utils.PrefrencesHelper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceGenerator {


    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit = null;

    private static int REQUEST_TIMEOUT = 60;


    private static void initOkHttp(final Context context) {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().
                setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor apiKeyHeaderInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json");

                // Adding Authorization token (API Key)
                // Requests will be denied without API key
                if (!TextUtils.isEmpty(PrefrencesHelper.getApiKey(context))) {
                    requestBuilder.addHeader("Authorization", PrefrencesHelper.getApiKey(context));
                }

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };

        httpClient.addInterceptor(loggingInterceptor);
        httpClient.addInterceptor(apiKeyHeaderInterceptor);

        okHttpClient = httpClient.build();

    }


    public static <S> S createService(Class<S> serviceClass, Context context) {

        if (okHttpClient == null)
            initOkHttp(context);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(UrlConfig.BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(serviceClass);
    }
}

