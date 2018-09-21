package com.example.newtest.ui.login;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    // 要申请的权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    /**
     * 获取动态权限的
     */
    public Boolean checkPermission() {
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                startRequestPermission();
                return false;
            }
        }
        return true;
    }

    // 开始提交请求权限
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }

    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults.length > 0) {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                        boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                        if (!b) {
                            // 用户还是想用我的 APP 的
                            // 提示用户去应用设置界面手动开启权限
                            showDialogTipUserGoToAppSettting();
                        } else
                            finish();
                    } else {
                        Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //TODO
//                    LogUtils.e(this, "权限获取失败,请重试", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // 提示用户去应用设置界面手动开启权限

    private void showDialogTipUserGoToAppSettting() {

        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle("存储权限不可用")
                .setMessage("请在-应用设置-权限-中，允许使用存储权限来保存用户数据")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);

        startActivityForResult(intent, 123);
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
                finish();
            }
        });

    }

    @Override
    public void loadData() {
        APPClientParam apm1 = new APPClientParam();
        apm1.setLogin_Name("14444441136").setLogin_Pwd(Codec.MD5.getMessageDigest("a123456".getBytes()));
        setRquestCanCanceledOnDestroy();
        presenter.doLogin(apm1);
        startRequestPermission();

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
