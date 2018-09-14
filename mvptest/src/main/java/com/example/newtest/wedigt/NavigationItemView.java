package com.example.newtest.wedigt;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.example.newtest.R;

/**
 * Created by Mark on 2018/7/12.
 */

public class NavigationItemView extends ConstraintLayout {
    private AppCompatTextView textView;
    private AppCompatImageView imageView;
    private View view;
    private int selectedColorRes = Color.RED;
    private int unSelectedColorRes = Color.GRAY;
    public NavigationItemView(Context context) {
        super(context);
        init(context);

    }

    public NavigationItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NavigationItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.item_navigation_view,this,true);
        textView = view.findViewById(R.id.textView);
        imageView = view.findViewById(R.id.img);
        this.view = view.findViewById(R.id.view);
        setClickable(true);

    }

    public NavigationItemView setDescription(String description) {
        textView.setText(description);
        return this;
    }
    public NavigationItemView setClickListener( OnClickListener l) {
        setOnClickListener(l);
        return this;
    }
    public NavigationItemView setDrawableSelectorRes(int drawableSelectorRes) {
        imageView.setImageResource(drawableSelectorRes);
        return this;
    }
   public  NavigationItemView  setDotViewShow(boolean isShow){
       view.setVisibility(isShow?VISIBLE:GONE);
       return this;
    }
    public NavigationItemView selected(boolean isSelected){
       imageView.setSelected(isSelected);
       if (isSelected){
           textView.setTextColor(selectedColorRes);
       }else{
           textView.setTextColor(unSelectedColorRes);
       }
       return this;
    }

}
