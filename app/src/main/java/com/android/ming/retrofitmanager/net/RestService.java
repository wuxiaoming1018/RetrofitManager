package com.android.ming.retrofitmanager.net;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * retrofit所有功能
 */
public interface RestService {
    @GET
    Call<String> get(@Url String url, @QueryMap Map<String,Object> params);

    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @FieldMap Map<String,Object> params);

    @FormUrlEncoded
    @PUT
    Call<String> put(@Url String url,@FieldMap Map<String,Object> params);

    @DELETE
    Call<String> delete(@Url String url, @QueryMap Map<String,Object> params);

    //下载默认是下载到运行内存，避免这种情况，需要注解@Streaming
    @Streaming
    @GET
    Call<ResponseBody> donload(@Url String url, @QueryMap Map<String,Object> params);

    //上传
    @Multipart
    @POST
    Call<String> upload(@Url String url, @Part MultipartBody.Part file);
}