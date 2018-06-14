package com.android.ming.retrofitmanager.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.android.ming.retrofitmanager.config.ProjectInit;
import com.android.ming.retrofitmanager.net.callback.IRequest;
import com.android.ming.retrofitmanager.net.callback.ISuccess;
import com.android.ming.retrofitmanager.util.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

public class SaveFileTask extends AsyncTask<Object,Void,File>{

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(IRequest request, ISuccess success) {
        REQUEST = request;
        SUCCESS = success;
    }

    @Override
    protected File doInBackground(Object... params) {
        String downloadDir = (String) params[0];
        String extension=(String)params[1];
        ResponseBody body=(ResponseBody)params[2];
        String name=(String)params[3];
        InputStream inputStream = body.byteStream();
        if (downloadDir == null||downloadDir.equals("")) {
            downloadDir = "down_loads";
        }

        if (extension == null) {
            extension = "";
        }
        if (name == null) {
            return FileUtil.writeToDisk(inputStream,downloadDir,extension.toUpperCase(),extension);
        }else{
            return FileUtil.writeToDisk(inputStream,downloadDir,name);
        }
    }

    //如果文件已经下载完毕

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUCCESS != null) {
            SUCCESS.onSuccess(file.getPath(),file.getAbsolutePath().toString());
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
        //如果下载的文件是apk，直接安装
        autoInstallApk(file);
    }

    private void autoInstallApk(File file) {
        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
            final Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
            ProjectInit.getApplicationContext().startActivity(intent);
        }
    }
}
