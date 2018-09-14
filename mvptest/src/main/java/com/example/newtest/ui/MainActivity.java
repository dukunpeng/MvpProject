package com.example.newtest.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.example.newtest.R;
import com.example.newtest.base.MvpBaseActivity;
import com.example.newtest.contract.MainActivityContract;
import com.example.newtest.ui.home.HomeFragment;
import com.example.newtest.ui.list.MainListFragment;
import com.example.newtest.net.HomePageObserver;
import com.example.newtest.net.NetWorkObservable;
import com.example.newtest.presenter.MainActivityPresenterImp;
import com.example.newtest.wedigt.NavigationItemView;
import com.example.newtest.wedigt.NavigationTabView;

/**
 * Created by Mark on 2018/7/12.
 */

public class MainActivity extends MvpBaseActivity<MainActivityPresenterImp> implements MainActivityContract.View {
    private NavigationTabView navigationTabView;

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showData(Object data) {

    }

    /**
     * 设置页面资源
     *
     * @return 资源ID
     */
    @Override
    public int getContentLayoutResourceId() {
        return R.layout.activity_main;
    }

    /**
     * 初始化Views
     */
    @Override
    public void initViews() {
        NetWorkObservable.getInstance().register(new HomePageObserver());
        navigationTabView = findViewById(R.id.navigation_view);
        initNavigation();
    }

    @Override
    public void loadData() {
    //    presenter.checkForUpdateSystem();
    }

    @Override
    public void initNavigation() {
        try {
            navigationTabView.setFragmentManager(getSupportFragmentManager())
                    .add(HomeFragment.class.newInstance(), new NavigationItemView(getActivity())
                            .setDotViewShow(false)
                            .selected(false)
                            .setDescription("首页")
                            .setDrawableSelectorRes(R.drawable.home_menu_selector))
                    .add(MainListFragment.class.newInstance(), new NavigationItemView(getActivity())
                            .setDotViewShow(false)
                            .selected(false)
                            .setDescription("列表")
                            .setDrawableSelectorRes(R.drawable.home_menu_selector))
                    .add(HomeFragment.class.newInstance(), new NavigationItemView(getActivity())
                            .setDotViewShow(false)
                            .selected(false)
                            .setDescription("3页")
                            .setDrawableSelectorRes(R.drawable.home_menu_selector))
                    .add(HomeFragment.class.newInstance(), new NavigationItemView(getActivity())
                            .setDotViewShow(false)
                            .selected(false)
                            .setDescription("我的")
                            .setDrawableSelectorRes(R.drawable.home_menu_selector))
            ;
            navigationTabView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    navigationTabView.showFragment(0);
                }
            }, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    void initFragment() {
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction();
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        ft.add(R.id.fragment_container, homeFragment);
        ft.addToBackStack(null);
        ft.commit();
        currentFragment = homeFragment;
    }

    private Fragment currentFragment;
    private Fragment homeFragment;

    //正确的做法
    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
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
