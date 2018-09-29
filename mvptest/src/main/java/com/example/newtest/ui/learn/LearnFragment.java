package com.example.newtest.ui.learn;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.example.newtest.R;
import com.example.newtest.base.AbstractMvpBaseFragment;
import com.example.newtest.contract.LearnContract;
import com.example.newtest.presenter.LearnPresenterImp;

/**
 * @author Mark
 * @create 2018/9/28
 * @Describe
 */

public class LearnFragment extends AbstractMvpBaseFragment<LearnPresenterImp> implements LearnContract.View {
    @Override
    public int getContentLayoutResourceId() {
        return R.layout.learn_fragment;
    }

    @Override
    public void loadData() {

    }

    @Override
    public void initViews() {

        addView();
    }



    @Override
    public void addView() {
        for (View view:
             presenter.createViews()) {
            ((LinearLayout)rootView).addView(view);
        }
    }

    @Override
    public Context context() {
        return getContext();
    }
}
