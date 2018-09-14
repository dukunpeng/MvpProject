package com.example.newtest.wedigt;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.newtest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 2018/7/12.
 */

public class NavigationTabView extends LinearLayout {
    private List<Fragment> fragmentList = new ArrayList<>();
    private FragmentManager fm;
    private Fragment currentFragment;
    public NavigationTabView(Context context) {
        super(context);
        init(context);
    }

    public NavigationTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NavigationTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NavigationTabView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);

    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        setClickable(true);
    }

    public NavigationTabView addTabItem(NavigationItemView view,int index) {
        LayoutParams layoutParams = new LayoutParams(0,LayoutParams.WRAP_CONTENT);
        layoutParams.weight=1;
        layoutParams.bottomMargin=5;
        layoutParams.topMargin =5;
        view.setTag(R.id.navigation_tag,index);
        addView(view,layoutParams);
        return this;
    }

    public NavigationItemView getTabView(int index) {
        try {
            return (NavigationItemView) getChildAt(index);
        } catch (Exception e) {

        }
        return null;
    }

    public NavigationTabView setFragmentManager(FragmentManager fm){

        this.fm = fm;
        return this;
    }



    public NavigationTabView add(final Fragment fragment, NavigationItemView navigationItemView) {
        navigationItemView.setClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                switchFragment(fragment);
                selected((Integer) v.getTag(R.id.navigation_tag));
            }
        });
        addTabItem(navigationItemView,fragmentList.size());
        fragmentList.add(fragment);
        return this;
    }
    void selected(int index){
        for (int i = 0; i <getChildCount() ; i++) {
            if (i==index){
                getTabView(i).selected(true);
            }else{
                getTabView(i).selected(false);
            }
        }
    }
    public NavigationTabView showFragment(int index){
        if (index>=0&&index<fragmentList.size()){
            FragmentTransaction ft = fm.beginTransaction();
            if (fm.getFragments()==null||fm.getFragments().size()<=0){
                for (int i = 0; i <fragmentList.size() ; i++) {
                    if (fragmentList.get(i).isAdded())
                        ft.remove(fragmentList.get(i));
                }
                ft.add(R.id.fragment_container, fragmentList.get(index));
                ft.addToBackStack(null);
                ft.commitAllowingStateLoss();
            }else{
                for (int i = 0; i <fragmentList.size() ; i++) {
                    if (fragmentList.get(i).isAdded())
                        ft.hide(fragmentList.get(i));
                }
                ft.show(fragmentList.get(index));
                ft.addToBackStack(null);
                fragmentList.get(index).onResume();
                ft.commitAllowingStateLoss();
            }
            currentFragment = fragmentList.get(index);
        }

        selected(index);

        return this;
    }
    //正确的做法
    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = fm
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction
                    .hide(currentFragment)
                    .add(R.id.fragment_container, targetFragment)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
            System.out.println("还没添加呢");
        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
            System.out.println("添加了( ⊙o⊙ )哇");
        }
        currentFragment = targetFragment;
    }

}
