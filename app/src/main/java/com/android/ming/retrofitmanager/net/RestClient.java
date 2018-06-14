package com.android.ming.retrofitmanager.net;

import com.android.ming.retrofitmanager.net.callback.IError;
import com.android.ming.retrofitmanager.net.callback.IFailure;
import com.android.ming.retrofitmanager.net.callback.IRequest;
import com.android.ming.retrofitmanager.net.callback.ISuccess;
import com.android.ming.retrofitmanager.net.callback.RequestCallbacks;
import com.android.ming.retrofitmanager.net.download.DownLoadHandler;

import java.io.File;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class RestClient {
    private final HashMap<String, Object> PARAMS;
    private final String URL;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    //上传下载
    private final File FILE;

    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String FILENAME;

    public RestClient(HashMap<String, Object> params, String url,
                      IRequest request, ISuccess success,
                      IFailure failure, IError error,
                      RequestBody body, File file,
                      String downloadDir, String extension,
                      String filename) {
        PARAMS = params;
        URL = url;
        REQUEST = request;
        SUCCESS = success;
        FAILURE = failure;
        ERROR = error;
        BODY = body;
        FILE = file;
        DOWNLOAD_DIR = downloadDir;
        EXTENSION = extension;
        FILENAME = filename;
    }

    public static RestClientBuilder create() {
        return new RestClientBuilder();
    }

    /**
     * 实现网络请求
     *
     * @param method 请求方法
     */
    private void request(HttpMethod method) {
        final RestService service = RestCreeator.getRestService();
        Call<String> call = null;
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }
        switch (method) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case DELETE:
                call = service.put(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MultipartBody.FORM, FILE);
                final MultipartBody.Part body = MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                call = service.upload(URL, body);
                break;
            default:
                break;
        }
        if (call != null) {
            call.enqueue(getRequestCallback());
        }
    }

    private Callback<String> getRequestCallback() {
        return new RequestCallbacks(REQUEST, SUCCESS, FAILURE, ERROR);
    }

    //各种请求
    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        request(HttpMethod.POST);
    }

    public final void put() {
        request(HttpMethod.PUT);
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void upload() {
        request(HttpMethod.UPLOAD);
    }

    public final void download() {
        new DownLoadHandler(PARAMS, URL, REQUEST,
                SUCCESS, FAILURE, ERROR,
                DOWNLOAD_DIR, EXTENSION, FILENAME)
                .handleDownload();
    }
}
