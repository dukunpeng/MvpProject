package com.example.newtest.window;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.newtest.R;
import com.example.newtest.apps.MyApplication;

import java.util.logging.Handler;

/**
 * @author Mark
 * @create 2018/9/14
 * @Describe
 */

public class SingleLoadingDialog extends Dialog implements ILoading
{


    private volatile static SingleLoadingDialog instance;

    private static View contentView;
    private boolean isDialogLoading;

    private AppCompatTextView textView;
    private AppCompatImageView imageView;
    private Animation animation;

    public Animation getAnimation() {
        if (animation==null){
            animation = AnimationUtils.loadAnimation(getContext(), R.anim.circle_normal);
        }
        return animation;
    }


    public SingleLoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static SingleLoadingDialog getInstance() {
        if (instance==null){
            synchronized (SingleLoadingDialog.class){
                if (instance==null){
                    instance = new SingleLoadingDialog(MyApplication.getInstance().getCurrentActivity(), R.style.dialog_fullscreen_tran);
                    instance.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
                    instance.setContentView(LayoutInflater.from(MyApplication.getInstance().getApplicationContext()).inflate(R.layout.window_loading_dialog,null));
                    instance.setCancelable(true);
                    instance.setCanceledOnTouchOutside(false);
                }
            }
        }
        instance.refreshLoadingState();
        return instance;
    }
    void refreshLoadingState(){
        if (!isDialogLoading){
            //TODO 动画复位
            getImageView().clearAnimation();
        }
    }

    public AppCompatTextView getTextView() {
        if (textView==null){
            textView = findViewById(R.id.textView);
        }
        return textView;
    }

    public AppCompatImageView getImageView() {
        if (imageView==null){
            imageView = findViewById(R.id.imageView);
        }
        return imageView;
    }

    @Override
    public void showLoading() {
                show();
                startLoad();

    }

    @Override
    public void hideLoad() {

        finishLoad();
        dismiss();
    }

    @Override
    public void pause() {


    }

    @Override
    public void startLoad() {

        getImageView().startAnimation(getAnimation());
    }

    @Override
    public void finishLoad() {

        getImageView().clearAnimation();
    }
}
