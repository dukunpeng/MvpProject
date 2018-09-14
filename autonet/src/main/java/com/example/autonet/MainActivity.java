package com.example.autonet;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.autonet.contentprovider.IncomingSmsObserver;

import java.util.HashMap;
import java.util.Map;

import static com.example.autonet.contentprovider.IncomingSmsObserver.INTENT_FILTER_MESSAGE_AUTH_CODE;
import static com.example.autonet.contentprovider.IncomingSmsObserver.PARAM_INTENT_AUTH_CODE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        getContentResolver().registerContentObserver(Uri.parse("content://sms"),true,new IncomingSmsObserver(this,new Handler()));
        LocalBroadcastManager.getInstance(this).registerReceiver(rideCode,
                new IntentFilter(INTENT_FILTER_MESSAGE_AUTH_CODE));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(rideCode);
    }

    private BroadcastReceiver rideCode = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String code = intent.getStringExtra(PARAM_INTENT_AUTH_CODE);
            webView.loadUrl("javascript:$(\"[placeholder='动态码：']\").val("+code+");");
            Toast.makeText(MainActivity.this,"验证码"+code,Toast.LENGTH_LONG).show();

        }
    };

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        // webView.addJavascriptInterface(new Handler(), "handler");
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // webView.getSettings().setCacheMode(WebSettings.LOAD_NORMAL);

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        //向js传递对象
        // webView.addJavascriptInterface(new JsInterface(),"skipToCF");
     //   webView.addJavascriptInterface(new JsInterface(),"callAppInterface");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }

        if (Build.VERSION.SDK_INT >= 19) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

     //   webView.getSettings().setTextZoom(100);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.equals("http://192.168.103.3:8099/portal/redirect/nacc/192.168.103.")){
                    webView.loadUrl(url+"?act=toDynamicLoginPage");
                }else{

                    autoInput(view);
                }
//                String s ="6221887970011792281";
//                     view.loadUrl("javascript:$(\"[placeholder='请输入您本人的储蓄卡号']\").focus().val('"+s+"').fireEvent(\"onchange\");");
//                   view.loadUrl("javascript:$(\"[placeholder='自动识别 无需输入']\").val('中国邮政银行');");
//                        view.loadUrl("javascript:$(\"[placeholder='请输入银行预留手机号']\").val('15618055521');");

            }
        });
        webView.loadUrl("http://192.168.103.3:8099/portal/redirect/nacc/192.168.103.");
        crudDatabase("15618055521","530303");
        updateOrInsert("15618055521","530303");
        findViewById(R.id.click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoInput(webView);
            }
        });
    }

    private void autoInput(final WebView webView){

        HashMap<String,String> map = getData();
        final String phone = map.get("phone");
        final String code = map.get("code");
        String time = map.get("time");

        if (!TextUtils.isEmpty(time)){
            if (System.currentTimeMillis()-Long.parseLong(time)<8*60*60*1000){
                //填写
                webView.loadUrl("javascript:$(\"[placeholder='动态码：']\").val("+code+");");
            }
                    webView.loadUrl("javascript:$(\"[placeholder='手机号码：']\").trigger(\"click\").focus();");
                    webView.loadUrl("javascript:$(\"[placeholder='手机号码：']\").focus().val("+phone+");");
                    webView.loadUrl("javascript:$(\"[id='phonenumber']\").val("+phone+");");
                    webView.loadUrl("javascript:$('#phonenumber').val("+phone+");");
                    webView.loadUrl("javascript:document.getElementById('phonenumber').focus();");
                    webView.loadUrl("javascript:document.getElementById('phonenumber').value='"+phone+"';");
                    webView.loadUrl("javascript:document.getElementById('validatecode').value='"+code+"';");
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//
//                    imm.showSoftInput(webView, 0);

        }


    }
    private void setCache(String phone,String code){

        SharePreferenceUtils.keepContent(this,SharePreferenceUtils.PREFERENCES_CACHE_PHONE,phone);
        SharePreferenceUtils.keepContent(this,SharePreferenceUtils.PREFERENCES_CACHE_CODE+phone,code);

    }
    private String TABLE_NAME= "person";
    private final String CREATE_TABLE_PERSON= "CREATE TABLE if not exists "+ TABLE_NAME+"("+
            "phone VARCHAR(15) PRIMARY KEY,"
            +"code VARCHAR(6) not null,"
            +"time VARCHAR(20) "
            +")";
    private void crudDatabase(String phone,String code){
        //打开或创建数据库
        SQLiteDatabase db=openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null);
     //   db.execSQL("DROP TABLE IF EXISTS person");
        //创建表
        db.execSQL(CREATE_TABLE_PERSON);

        //带占位符的插入语句
    //    db.execSQL("insert into person values('?','?','?')", new Object[]{phone,code,""+System.currentTimeMillis()});

        db.close();
    }
    private void updateOrInsert(String phone,String code){
        //打开或创建数据库
        SQLiteDatabase db=openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null);

      Cursor cursor = db.query("person",new String[]{"phone","code","time"}," phone = ?",new String[]{phone},null,null,null);
      if (cursor.moveToNext()){
          //改
          ContentValues contentValues = new ContentValues();
          contentValues.put("phone",phone);
          contentValues.put("code",code);
          //这里这样判断 会有很小的几率出现两天的验证码撞脸的情况
          if (!"code".equals(cursor.getString(cursor.getColumnIndex("code")))){
              contentValues.put("time",""+System.currentTimeMillis());
          }
          db.update("person",contentValues,null,null);
      }else{
          //insert
          ContentValues contentValues = new ContentValues();
          contentValues.put("phone",phone);
          contentValues.put("code",code);
          contentValues.put("time",""+System.currentTimeMillis());
          db.insert("person",null,contentValues);
      }
      db.close();

    }
    private HashMap<String,String> getData(){
        //打开或创建数据库
        SQLiteDatabase db=openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null);
       // db.execSQL("DROP TABLE IF EXISTS person");

        Map map = new HashMap();
      Cursor cursor =   db.query("person",null,null,null,null,null,null);
        if (cursor.moveToNext()){
            map.put("phone",cursor.getString(cursor.getColumnIndex("phone")));
            map.put("code",cursor.getString(cursor.getColumnIndex("code")));
            map.put("time",cursor.getString(cursor.getColumnIndex("time")));
        }
        db.close();
        return (HashMap<String, String>) map;
    }

}
