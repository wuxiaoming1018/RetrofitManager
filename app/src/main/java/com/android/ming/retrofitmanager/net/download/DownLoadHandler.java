package com.android.ming.retrofitmanager.net.download;

import android.os.AsyncTask;

import com.android.ming.retrofitmanager.net.RestCreeator;
import com.android.ming.retrofitmanager.net.callback.IError;
import com.android.ming.retrofitmanager.net.callback.IFailure;
import com.android.ming.retrofitmanager.net.callback.IRequest;
import com.android.ming.retrofitmanager.net.callback.ISuccess;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class DownLoadHandler {
    private final Map<String, Object> PARAMS; //地址参数
    private final String URL;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final String DOWNLOAD_DIR; //下载文件夹
    private final String EXTENSION; //扩展名
    private final String FILENAME; //文件名


    public DownLoadHandler(Map<String, Object> params, String url,
                           IRequest request, ISuccess success,
                           IFailure failure, IError error,
                           String download_dir, String extension,
                           String filename) {
        PARAMS = params;
        URL = url;
        REQUEST = request;
        SUCCESS = success;
        FAILURE = failure;
        ERROR = error;
        DOWNLOAD_DIR = download_dir;
        EXTENSION = extension;
        FILENAME = filename;
    }

    public final void handleDownload(){
        RestCreeator.getRestService().download(URL,PARAMS)
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                                    DOWNLOAD_DIR,EXTENSION,response.body(),FILENAME);
                            //如果下载完成
                            if (task.isCancelled()) {
                                if (REQUEST != null) {
                                    REQUEST.onRequestEnd();
                                }
                            }
                        }else{
                            if (ERROR != null) {
                                ERROR.onError(response.code(),response.message(),call.request().url().toString());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE != null) {
                            FAILURE.onFailure();
                        }
                    }
                });
    }
}
