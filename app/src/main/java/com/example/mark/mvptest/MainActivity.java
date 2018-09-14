package com.example.mark.mvptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mark.mvptest.base.APPClientParam;
import com.example.mark.mvptest.base.BaseCallBack;
import com.example.mark.mvptest.kit.Codec;
import com.example.mark.mvptest.log.XLog;
import com.example.mark.mvptest.manager.DataModelManager;
import com.example.mark.mvptest.request.OkHttpClientManager;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Map<String,String> map = new HashMap<>();
        map.put("str","{\"deviceId\": \"DBE01FDF-5FD3-4E15-8DF9-5AE79B9F74F7\", \"clienTime\": \"2018-02-05 15:23:52\", \"singnStr\": \"77af9fc893af5ba3569061044d1a21f8\", \"code\": \"Unify_User_Login\", \"platform\": \"2\", \"isEncrypt\": \"false\", \"isCompress\": \"false\", \"paramStr\": \"{\\\"device_name\\\": \\\"mk 6\\\", \\\"Login_Name\\\": \\\"15022222222\\\", \\\"Login_Pwd\\\": \\\"dc483e80a7a0bd9ef71d8cf973673924\\\", \\\"Memo\\\": \\\"1517bfd3f7c06ce77ad\\\"}\", \"version\": \"4.0.3_170\", \"clientDataMD5\": \"1F61F9C58188FB9D18A2BE2B28C363A5\"}");
        OkHttpClientManager.getInstance().postAsyn("https://mp.hepandai.com/Base/InvokeWS", new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                XLog.json(response);
                another();
            }

            @Override
            public void onFailure(Exception e) {

            }
        },map);
        APPClientParam apm = new APPClientParam();
        apm.setLogin_Name("14444440021");
        apm.setPassword(Codec.MD5.getMessageDigest(("a123456"+"HPD").getBytes()));
        DataModelManager.newInstance(LoginDataModel.class.getName()).params(apm).execute(new OkHttpClientManager.ResultCallback() {
            @Override
            public void onSuccess(Object response) {

            }

            @Override
            public void onFailure(Exception e) {

            }

        });
    }
    private void another(){
        Map<String,String> map = new HashMap<>();
        map.put("str","{\"deviceId\": \"DBE01FDF-5FD3-4E15-8DF9-5AE79B9F74F7\", \"clienTime\": \"2018-02-05 15:23:52\", \"singnStr\": \"f5c94e6d9e3b592ffc8f83374e56d53c\", \"code\": \"GetMyHPD\", \"platform\": \"2\", \"isEncrypt\": \"false\", \"isCompress\": \"false\", \"paramStr\": \"{'IntId': 1}\", \"version\": \"4.0.3_170\", \"clientDataMD5\": \"1F61F9C58188FB9D18A2BE2B28C363A5\"}");
        OkHttpClientManager.getInstance().postAsyn("https://mp.hepandai.com/Base/InvokeWS", new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                XLog.json(response);
            }

            @Override
            public void onFailure(Exception e) {

            }
        },map);

    }
}
