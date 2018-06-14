package com.android.ming.retrofitmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.ming.retrofitmanager.net.RestClient;
import com.android.ming.retrofitmanager.net.callback.IError;
import com.android.ming.retrofitmanager.net.callback.ISuccess;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //.success() .error() 以及.failure()可以自行决定添加与否
    public void click(View view){
        HashMap params = new HashMap();
        params.put("type","yuantong");
        params.put("postid","500379523313");
        RestClient.create()
                .url("/query")
                .params(params)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response,String url) {
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg,String url) {
                        Toast.makeText(MainActivity.this, "code="+code+",message="+msg+",url="+url, Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .post();
    }
}
