package com.github.lianjiatech.retrofit.spring.boot.test.http;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.Intercept;
import com.github.lianjiatech.retrofit.spring.boot.test.entity.Person;
import com.github.lianjiatech.retrofit.spring.boot.test.entity.Result;
import com.github.lianjiatech.retrofit.spring.boot.test.ex.TestErrorDecoder;
import com.github.lianjiatech.retrofit.spring.boot.test.interceptor.Sign;
import com.github.lianjiatech.retrofit.spring.boot.test.interceptor.TimeStamp2Interceptor;
import com.github.lianjiatech.retrofit.spring.boot.test.interceptor.TimeStampInterceptor;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author 陈添明
 */
@RetrofitClient(baseUrl = "${test.baseUrl}", poolName = "test1", errorDecoder = TestErrorDecoder.class)
@Sign(accessKeyId = "${test.accessKeyId}", accessKeySecret = "${test.accessKeySecret}", exclude = {"/api/test/query"})
@Intercept(handler = TimeStampInterceptor.class)
@Intercept(handler = TimeStamp2Interceptor.class)
public interface HttpApi {

    /**
     * 其他任意Java类型 <br>
     * 将响应体内容适配成一个对应的Java类型对象返回，如果http状态码不是2xx，直接抛错！<br>
     *
     * @param id id
     * @return 其他任意Java类型
     */
    @GET("person")
    Result<Person> getPerson(@Query("id") Long id);

    /**
     * CompletableFuture<T> <br>
     * 将响应体内容适配成CompletableFuture<T>对象返回，异步调用
     *
     * @param id id
     * @return CompletableFuture<T>
     */
    @GET("person")
    CompletableFuture<Result<Person>> getPersonCompletableFuture(@Query("id") Long id);

    /**
     * Response<T> <br>
     * 将响应内容适配成Response<T>对象返回
     *
     * @param id id
     * @return Response<T> .
     */
    @GET("person")
    Response<Result<Person>> getPersonResponse(@Query("id") Long id);

    /**
     * Call<T> <br>
     * 不执行适配处理，直接返回Call<T>对象
     *
     * @param id id
     * @return Call<T>实例
     */
    @GET("person")
    Call<Result<Person>> getPersonCall(@Query("id") Long id);

    @POST("savePerson")
    Void savePersonVoid(@Body Person person);

    @POST("savePerson")
    Result<Void> savePerson(@Body Person person);

    @POST("error")
    Person error(@Body Person person);

    @POST("savePersonList")
    Result<Void> savePersonList(@Body List<Person> personList);

    @POST("getString")
    String getString(@Body Person person);

    @POST("getBoolean")
    Boolean getBoolean(@Body Person person);
}
