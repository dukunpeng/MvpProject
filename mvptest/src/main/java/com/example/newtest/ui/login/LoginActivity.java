package com.example.newtest.ui.login;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.newtest.R;
import com.example.newtest.base.MvpBaseActivity;
import com.example.newtest.common.APPClientParam;
import com.example.newtest.contract.LoginContract;
import com.example.newtest.kit.Codec;
import com.example.newtest.presenter.LoginPresenterImp;
import com.example.newtest.router.Router;
import com.example.newtest.ui.MainActivity;
import com.example.newtest.wedigt.LoadingAbleLayout;
import com.google.gson.Gson;

/**
 * Created by Mark on 2018/4/9.
 */

public class LoginActivity extends MvpBaseActivity<LoginPresenterImp> implements LoginContract.View{

    private TextView tvTest1,tvTest2,tv1,tv2,tv3;
    private LoadingAbleLayout loadingAbleLayout;

    /**
     * 设置页面资源
     *
     * @return 资源ID
     */
    @Override
    public int getContentLayoutResourceId() {
        return R.layout.activity_login_s;
    }

    @Override
    protected View getContentLayoutView() {
        return null;
    }

    /**
     * 初始化Views
     */
    @Override
    public void initViews() {

        loadingAbleLayout = findViewById(R.id.loadingAbleLayout);
        loadingAbleLayout.setText("正常状态");
        tvTest1 = findViewById(R.id.tv_test1);
        tvTest2 = findViewById(R.id.tv_test2);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APPClientParam apm1 = new APPClientParam();
                apm1.setLogin_Name("14444441136").setLogin_Pwd(Codec.MD5.getMessageDigest("a123456".getBytes()));
                setRquestCanCanceledOnDestroy();
                presenter.doLogin(apm1);
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*APPClientParam apm = new APPClientParam();
                apm.setMemo("order_4");
                apm.setStrID("11074");
                setRquestCanCanceledOnDestroy();
                presenter.getUserInfo(apm);*/
                loadingAbleLayout.onLoading();
                showLoading();

                loadData();
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingAbleLayout.onLoadingComplete();
                hideLoading();
                canceledRequests();
                Router.newIntent(LoginActivity.this).to(MainActivity.class).launch();
            }
        });

    }

    @Override
    public void loadData() {
        APPClientParam apm1 = new APPClientParam();
        apm1.setLogin_Name("14444441136").setLogin_Pwd(Codec.MD5.getMessageDigest("a123456".getBytes()));
        setRquestCanCanceledOnDestroy();
        presenter.doLogin(apm1);
//        APPClientParam apm = new APPClientParam();
//        apm.setMemo("order_4");
//        apm.setStrID("11074");
//        setRquestCanCanceledOnDestroy();
//        presenter.getUserInfo(apm);


    }



    @Override
    public void showError() {
        tvTest1.setBackgroundColor(Color.YELLOW);
    }


    @Override
    public void showData(Object data) {
        tvTest1.setText(new Gson().toJson(data));
    }


    @Override
    public void showRecycleList() {

    }

    @Override
    public void showBanner() {

    }
}
