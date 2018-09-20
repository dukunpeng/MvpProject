package com.example.newtest.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.newtest.R;
import com.example.newtest.common.ToolbarBgColor;
import com.example.newtest.kit.Kits;
import com.example.newtest.log.XLog;
import com.example.newtest.permission.PermissionActivity;
import com.example.newtest.request.OkHttpClientManager;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 2018/4/7.
 */

public abstract class BaseActivity extends PermissionActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        createAndInitViews();
        loadData();
        super.onCreate(savedInstanceState);
    }

    protected void createAndInitViews() {
        if (getContentLayoutResourceId() == 0) {
            if (getContentLayoutView() != null) {
                setContentView(getContentLayoutView());
            } else {
                XLog.e(this.getClass().getName()+"没有设置界面资源");
                throw new RuntimeException(this.getClass().getName()+"没有设置界面资源");
            }
        } else {
            setContentView(getContentLayoutResourceId());

        }
        //初始化UI
        initViews();
        if (getIntent()!=null){
            onIntentReceived(getIntent());
        }
        setListeners();

    }

    /**
     * 如果上个页面有数据传递，则在这里接收，如果是本Activity是单例，则不会调用，此处已做费控判断
     * @param intent
     */
    protected void onIntentReceived(Intent intent){

    }
    public void setListeners(){

    }
    private List<Integer> keyList = new ArrayList<Integer>(5);
    protected void setRquestCanCanceledOnDestroy(){
        keyList.add(OkHttpClientManager.getInstance().callSchedule.setNextCallCanCanceledTag());
    }


    public Context getActivity() {
        return this;
    }

    /**
     * 设置页面资源
     * @return 资源ID
     */
    public abstract int getContentLayoutResourceId();

    /**
     * 初始化Views
     */
    public abstract void initViews();
    public abstract void loadData();

    protected View getContentLayoutView() {
        return null;
    }

    protected Toolbar toolbar;

    protected void onCreateCustomToolBar(Toolbar toolbar) {
        toolbar.setContentInsetsRelative(0, 0);
    }

    protected void initToolbar(String title) {
        initToolbar(title, true, ToolbarBgColor.RED_BG_COLOR);
    }

    protected void initToolbar(String title, boolean isCJ) {
        initToolbar(title, isCJ, ToolbarBgColor.WHITE_BG_COLOR);
    }

    protected void initToolbar(String title, boolean isCJ, ToolbarBgColor toolbarBgColor) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        TextView tvBack = (TextView) findViewById(R.id.tvBack);
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        TextView tvRight = (TextView) toolbar.findViewById(R.id.tvDistance);

        toolbar.setBackgroundResource(toolbarBgColor.getColorResource());
        if (toolbarBgColor == ToolbarBgColor.RED_BG_COLOR) {
            tvBack.setTextColor(getResources().getColor(R.color.white));
            tvTitle.setTextColor(getResources().getColor(R.color.white));
            tvRight.setTextColor(getResources().getColor(R.color.white));
        } else if (toolbarBgColor == ToolbarBgColor.WHITE_BG_COLOR) {
            tvBack.setTextColor(getResources().getColor(R.color.important_text_color));
            tvTitle.setTextColor(getResources().getColor(R.color.important_text_color));
            tvRight.setTextColor(getResources().getColor(R.color.blue_theme_text_color));
        }

        if (!TextUtils.isEmpty(title)){

            tvTitle.setText("" + title);
        }
//        tvBack.setTypeface(HpApplication.getInstance().getIconTypeFace());

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
        setSupportActionBar(toolbar);
        onCreateCustomToolBar(toolbar);
        getSupportActionBar().show();
        if (isCJ) {
            int height = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                height = getStatusBarHeight(this);
            }
            toolbar.getLayoutParams().height = Kits.Dimens.dpToPxInt(this, 44) + height;
            initAfterSetContentView(this, toolbar);
        } else {
            toolbar.getLayoutParams().height = Kits.Dimens.dpToPxInt(this, 44);
        }
    }


    protected void showToolBar() {
        if (toolbar != null){
            //  setViewVisible(toolbar);
            getSupportActionBar().show();
        }

    }

    /**
     * 因为要处理 actionbar 变沉侵式时 底部的留白 这里让View 有刷新
     */
    protected void hideToolBar() {
        if (toolbar != null) {
            getSupportActionBar().hide();
            // AppPageUtil.initAfterSetContentView(this,toolbar);

        }
        //  setViewGone(toolbar);

    }


    protected void setToolbarBgColor(ToolbarBgColor toolbarBgColor) {

    }

    protected void setVisibilityGone(View view) {
        if (view != null){

            view.setVisibility(View.GONE);
        }
    }

    protected void setVisibilityVisible(View view) {
        if (view != null){

            view.setVisibility(View.VISIBLE);
        }
    }

    protected void setVisibilityInVisible(View view) {
        if (view != null){

            view.setVisibility(View.INVISIBLE);
        }
    }

    protected void canceledRequests(){
        for (int i = 0; i <keyList.size() ; i++) {
            OkHttpClientManager.getInstance().callSchedule.cancelCallWithKey(keyList.get(i));
        }
        keyList.clear();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
       canceledRequests();
    }

    /**
     * Request the system permissions.
     */
    public void requestPermissions() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},//需要请求的所有权限，需要请求多个请添加对应权限
                    0//请求码
            );
        }

    }

    /**
     * 在{@link Activity#setContentView}之后调用
     *
     * @param activity       要实现的沉浸式状态栏的Activity
     * @param titleViewGroup 头部控件的ViewGroup,若为null,整个界面将和状态栏重叠
     */


    protected void initAfterSetContentView(Activity activity,

                                           View titleViewGroup) {
        try {
            if (activity == null){

                return;
            }


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                Window window = activity.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor("#19000000"));
                if (titleViewGroup == null){

                    return;
                }


                // 设置头部控件ViewGroup的PaddingTop,防止界面与状态栏重叠

                int statusBarHeight = getStatusBarHeight(activity);

              /*  if (titleViewGroup instanceof Toolbar){
                    ((Toolbar)titleViewGroup).measure(UIUtils.getSysScreen(activity).x,UIUtils.dip2px(activity,64f));
                }*/
                titleViewGroup.setPadding(0, statusBarHeight, 0, 0);

            }

        } catch (Exception e) {

        }

    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */

    private int getStatusBarHeight(Context context) {

        int result = 0;

        int resourceId = context.getResources().getIdentifier(

                "status_bar_height", "dimen", "android");

        if (resourceId > 0) {

            result = context.getResources().getDimensionPixelSize(resourceId);

        }

        return result;

    }
}
