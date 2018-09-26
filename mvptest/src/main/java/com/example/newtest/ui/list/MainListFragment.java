package com.example.newtest.ui.list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.newtest.R;
import com.example.newtest.adapter.base.BaseRecycleAdapter;
import com.example.newtest.base.AbstractMvpBaseFragment;
import com.example.newtest.bean.UserData;
import com.example.newtest.contract.MainListContract;
import com.example.newtest.presenter.MainListPresenterImp;

import java.util.HashMap;
import java.util.List;


/**
 * @author Mark
 * @create 2018/9/13
 * @Describe
 */

public class MainListFragment extends AbstractMvpBaseFragment<MainListPresenterImp> implements MainListContract.View{

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
        try{
            Thread.sleep(1*1000);
        }catch (Exception e){

        }
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
