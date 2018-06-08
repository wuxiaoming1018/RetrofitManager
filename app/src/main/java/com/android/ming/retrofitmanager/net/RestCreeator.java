package com.android.ming.retrofitmanager.net;

import com.android.ming.retrofitmanager.config.ConfigKey;
import com.android.ming.retrofitmanager.config.ProjectInit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestCreeator {
    //产生一个全局客户端
    private static final class RetrofitHolder{
        private static final String BASE_URL = ProjectInit.getConfiguration(ConfigKey.API_HOST.name());
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();//后面需要GSON解析器，rxjava再继续添加
    }

    private static final class OKHttpHolder{
        private static final int TIME_OUT = 20;//超时时间
        private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)//连接超时 单位是秒
                .writeTimeout(TIME_OUT,TimeUnit.SECONDS)//读取超时
                .build();
    }


    private static final class RestServiceHolder{
        private static final RestService REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    /**
     * 获取RestService对象
     * @return
     */
    public static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }
}
