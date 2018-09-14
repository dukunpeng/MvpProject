package com.example.newtest.wedigt;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.newtest.R;
import com.example.newtest.kit.Kits;

/**
 * Created by Mark on 2018/7/10.
 */

public class LoadingAbleLayout extends RelativeLayout implements LoadingAbleInterface {

    /**
     * 请求时展示的动画资源
     */
    private int loadingAnimalRes;
    private Animation animation;

    private float textSize = 16;
    /**
     * 展示动画的图片资源
     */
    private int animationViewBackgroundResource;

    public LoadingAbleLayout(Context context) {
        super(context);
        init(context);
    }

    public LoadingAbleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initParams(context,attrs);
        init(context);
    }

    public LoadingAbleLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context,attrs);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LoadingAbleLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initParams(context,attrs);
        init(context);
    }

    private void init(Context context){
        addView(new TextView(context),new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        if (textSize==0){
            textSize =16;
        }
        ((TextView)getChildAt(0)).setTextSize(TypedValue.COMPLEX_UNIT_DIP,textSize );
        ((TextView)getChildAt(0)).setTextColor(Color.WHITE);
        ((TextView)getChildAt(0)).setGravity(Gravity.CENTER);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        int m =Kits.Dimens.dpToPxInt(context,5f);
        layoutParams.setMargins(m,m,m,m);
        addView(new AppCompatImageView(context),layoutParams);
        ((AppCompatImageView)getChildAt(1)).setScaleType(AppCompatImageView.ScaleType.CENTER_INSIDE);
        ((AppCompatImageView)getChildAt(1)).setImageResource(animationViewBackgroundResource);
        getChildAt(1).setVisibility(GONE);
    }
    public void setText(String character){
        if(getChildAt(0)!=null){
            ((TextView)getChildAt(0)).setText(character);

        }
    }
    public void setTextSize(float textSize){
        this.textSize = textSize;
        ((TextView)getChildAt(0)).setTextSize(TypedValue.COMPLEX_UNIT_DIP,textSize );
    }

    private void initParams(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingAbleLayout);
        if (typedArray != null) {
            loadingAnimalRes = typedArray.getResourceId(R.styleable.LoadingAbleLayout_loading_animation, 0);
            animationViewBackgroundResource = typedArray.getResourceId(R.styleable.LoadingAbleLayout_animationView_backgroundResource, 0);
            textSize = typedArray.getDimension(R.styleable.LoadingAbleLayout_text_size,0);
        }
    }

    @Override
    public void onLoading() {

        if (loadingAnimalRes != 0) {
            animation = AnimationUtils.loadAnimation(getContext(), loadingAnimalRes);
            if (animation!=null){
                ((TextView)getChildAt(0)).setTextColor(Color.TRANSPARENT);
                getChildAt(1).setVisibility(VISIBLE);
              getChildAt(1).startAnimation(animation);
            }
        }
    }

    @Override
    public void onLoadingComplete() {
        if (animation != null) {
            getChildAt(1).clearAnimation();
            getChildAt(1).setVisibility(GONE);
            animation.cancel();
            ((TextView)getChildAt(0)).setTextColor(Color.WHITE);
        }
    }


}
