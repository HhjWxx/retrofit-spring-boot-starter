package com.github.lianjiatech.retrofit.spring.boot.test.interceptor;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.github.lianjiatech.retrofit.spring.boot.interceptor.BasePathMatchInterceptor;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author 陈添明
 */
@Component
public class TimeStamp2Interceptor extends BasePathMatchInterceptor {

    @Override
    public Response doIntercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url();
        long timestamp = System.currentTimeMillis();
        HttpUrl newUrl = url.newBuilder()
                .addQueryParameter("timestamp2", String.valueOf(timestamp))
                .build();
        Request newRequest = request.newBuilder()
                .url(newUrl)
                .build();
        return chain.proceed(newRequest);
    }
}
