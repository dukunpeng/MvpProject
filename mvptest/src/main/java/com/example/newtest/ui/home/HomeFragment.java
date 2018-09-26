package com.example.newtest.ui.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.newtest.R;
import com.example.newtest.adapter.base.BaseRecycleAdapter;
import com.example.newtest.base.AbstractMvpBaseFragment;
import com.example.newtest.contract.HomeContract;
import com.example.newtest.presenter.HomePresenterImp;
import com.example.newtest.router.Router;
import com.example.newtest.ui.test.ActivityOne;
import com.example.newtest.ui.test.ActivityTwo;

import java.util.List;

/**
 * Created by Mark on 2018/7/13.
 */

public class HomeFragment extends AbstractMvpBaseFragment<HomePresenterImp> implements HomeContract.View {
    private RecyclerView recyclerView;


    private BaseRecycleAdapter adapter;
    @Override
    public int getContentLayoutResourceId() {
        return R.layout.fragment_home;
    }

    @Override
    public void loadData() {

        presenter.requestBanner();
        presenter.getList();
    }

    @Override
    public void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new BaseRecycleAdapter<String>(getActivity(),null,R.layout.main_list_item_list,true) {

            @Override
            protected void setPositionClick(int position, String bean) {

                switch (position){
                    case 0:
                        Router.newIntent(getActivity()).to(ActivityOne.class).launch();
                        break;
                    case 1:
                        Router.newIntent(getActivity()).to(ActivityTwo.class).launch();
                        break;
                }
            }

            @Override
            protected void initData(MyViewHolder holder, int position, String bean) {

                holder.setText(R.id.text,bean);
            }
        };
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void showList(List<String> strings) {
        adapter.setList(strings);
        adapter.notifyDataSetChanged();
    }
}
