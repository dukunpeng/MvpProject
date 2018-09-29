package com.example.newtest.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * @author Mark
 * @create 2018/9/28
 * @Describe
 */

public class ItemTextView extends AppCompatTextView {

    public ItemTextView(Context context,String text,OnClickListener onClickListener) {
        super(context);
        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        setText(text);
        setPadding(25,25,25,25);
        setGravity(Gravity.CENTER);
        setOnClickListener(onClickListener);
    }


}
