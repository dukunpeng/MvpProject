package com.example.newtest.window;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.newtest.R;
import com.example.newtest.apps.MyApplication;

import java.lang.ref.WeakReference;

/**
 *
 * @author Mark
 * @create 2018/9/14
 * @Describe
 */

public class SingleLoadingDialog extends Dialog implements ILoading
{
    //TODO 这里需要重新设计下，因为这样子还是会内存泄漏

    private volatile static SingleLoadingDialog instance;
private WeakReference<Context> contextWeakReference;

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
        contextWeakReference = new WeakReference<Context>(context);
    }

    /**
     * 因为要兼容老版本手机，目前只做到Activity内的单例
     * @return
     */
    public static SingleLoadingDialog getInstance() {
        if (instance==null){
            synchronized (SingleLoadingDialog.class){
                if (instance==null){

                    CreateDialog.invoke();
                }else{
                    if (instance.contextWeakReference==null||!MyApplication.getInstance().getCurrentActivity().equals(instance.contextWeakReference.get())){
                        instance.dismiss();
                        CreateDialog.invoke();
                    }
                }
            }
        }else{
            if (instance.contextWeakReference==null||!MyApplication.getInstance().getCurrentActivity().equals(instance.contextWeakReference.get())){

                instance.dismiss();
                CreateDialog.invoke();
            }
        }
        instance.refreshLoadingState();
        return instance;
    }

    void refreshLoadingState(){
            //TODO 动画复位
            getImageView().clearAnimation();
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
        if (getContext()!=null){
            show();
            startLoad();
        }

    }

    @Override
    public void hideLoad() {
        stopLoad();
        dismiss();
    }


    protected void startLoad() {

        getImageView().startAnimation(getAnimation());
    }

    protected void stopLoad() {

        getImageView().clearAnimation();
    }

    private static class CreateDialog {
        private static void invoke() {
            instance = new SingleLoadingDialog(MyApplication.getInstance().getCurrentActivity(), R.style.dialog_fullscreen_tran);
            // instance.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
            instance.setContentView(LayoutInflater.from(MyApplication.getInstance().getApplicationContext()).inflate(R.layout.window_loading_dialog,null));
            instance.getImageView()/*.setTag(MyApplication.getInstance().getCurrentActivity())*/;
            instance.getTextView();
            instance.setCancelable(true);
            instance.setCanceledOnTouchOutside(false);

            /*instance.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    switch (keyCode){
                        case KeyEvent.KEYCODE_BACK:
                            instance.dismiss();
                            return true;
                    }
                    return false;
                }
            });*/
        }
    }
}
