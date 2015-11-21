package com.example.tinoba.liveball.retrofit;

import android.util.Base64;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by antonio on 21/11/15.
 */

public class ServiceGenerator {

    public static final String API_BASE_URL = "http://46.101.223.108:1440/";

    private static OkHttpClient httpClient = new OkHttpClient();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.interceptors().add(logging);


        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}





//public class ServiceGenerator {
//
//    public static final String API_BASE_URL = "http://46.101.223.108:1440/";
//
//    private static OkHttpClient httpClient = new OkHttpClient();
//    private static Retrofit.Builder builder =
//            new Retrofit.Builder()
//                    .baseUrl(API_BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create());
//
//    public static <S> S createService(Class<S> serviceClass) {
//        return createService(serviceClass, null, null);
//    }
//
//    public static <S> S createService(Class<S> serviceClass, String username, String password) {
//        if (username != null && password != null) {
//            String credentials = username + ":" + password;
//            final String basic =
//                    "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
//
//            httpClient.interceptors().clear();
//
//            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//            httpClient.interceptors().add(logging);
//
//            httpClient.interceptors().add(new Interceptor() {
//                @Override
//                public Response intercept(Interceptor.Chain chain) throws IOException {
//                    Request original = chain.request();
//
//                    Request.Builder requestBuilder = original.newBuilder()
//                            .header("Authorization", basic)
//                            .header("Accept", "applicaton/json")
//                            .method(original.method(), original.body());
//
//                    Request request = requestBuilder.build();
//                    return chain.proceed(request);
//                }
//            });
//        }
//
//        Retrofit retrofit = builder.client(httpClient).build();
//        return retrofit.create(serviceClass);
//    }
//}


