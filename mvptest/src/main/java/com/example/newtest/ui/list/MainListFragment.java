package com.example.newtest.ui.list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.newtest.R;
import com.example.newtest.adapter.base.BaseRecycleAdapter;
import com.example.newtest.base.BaseFragment;
import com.example.newtest.base.MvpBaseFragment;
import com.example.newtest.bean.UserData;
import com.example.newtest.contract.MainListContract;
import com.example.newtest.presenter.MainListPresenterImp;
import com.example.newtest.window.SingleLoadingDialog;

import java.util.HashMap;
import java.util.List;


/**
 * @author Mark
 * @create 2018/9/13
 * @Describe
 */

public class MainListFragment extends MvpBaseFragment<MainListPresenterImp> implements MainListContract.View{

    private RecyclerView recyclerView;


    private BaseRecycleAdapter adapter;
    @Override
    public int getContentLayoutResourceId() {
        return R.layout.list_fragment;
    }

    @Override
    public void loadData() {
        presenter.getList();
    }

    @Override
    public void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new BaseRecycleAdapter<UserData>(getActivity(),null,R.layout.main_list_item_list,true) {

            @Override
            protected void setPositionClick(int position, UserData bean) {

            }

            @Override
            protected void initData(MyViewHolder holder, int position, UserData bean) {

                holder.setText(R.id.text,bean.getRealName());
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showLoading() {

        SingleLoadingDialog.getInstance().showLoading();
    }

    @Override
    public void hideLoading() {

        SingleLoadingDialog.getInstance().hideLoad();
    }

    @Override
    public void showError() {

    }

    @Override
    public void showData(Object data) {

    }

    @Override
    public void showList(List<UserData> list) {

        adapter.setList(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public HashMap<String, String> getParam() {
        return null;
    }
}
