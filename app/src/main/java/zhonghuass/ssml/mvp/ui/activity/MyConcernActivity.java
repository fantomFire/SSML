package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.refreshView.internal.Utils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerMyConcernComponent;
import zhonghuass.ssml.di.module.MyConcernModule;
import zhonghuass.ssml.mvp.contract.MyConcernContract;
import zhonghuass.ssml.mvp.model.appbean.ConcernFansBean;
import zhonghuass.ssml.mvp.presenter.MyConcernPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.mvp.ui.adapter.ConcernFansAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MyConcernActivity extends MBaseActivity<MyConcernPresenter> implements MyConcernContract.View, BaseQuickAdapter.RequestLoadMoreListener {
    private String mId = "1";
    private String mType = "1";
    private int page = 1;
    private List<ConcernFansBean> mList = new ArrayList<>();

    @BindView(R.id.rv_concern)
    RecyclerView rvConcern;
    @BindView(R.id.srl_concern)
    SwipeRefreshLayout swipeRefreshLayout;
    private ConcernFansAdapter mAdapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyConcernComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myConcernModule(new MyConcernModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_concern; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolBar("我的关注");

        rvConcern.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ConcernFansAdapter(R.layout.item_concern_fans, mList);
        mAdapter.setOnLoadMoreListener(this);

        mAdapter.setNoDateGone(this, 80, 45);

        rvConcern.setAdapter(mAdapter);
        mPresenter.getMyConcernData(mId, mType, page);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mPresenter.getMyConcernData(mId, mType, page);
            }
        });
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void showDate(List<ConcernFansBean> data) {
        List<ConcernFansBean> data2 = new ArrayList<>();

        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }

        if (data.size() > 0) {
            mAdapter.loadMoreComplete();
        } else {
            mAdapter.loadMoreEnd();
            return;
        }

        for (int i = 0; i < 1; i++) {
            data2.add(data.get(i));
        }

        if (page == 1) {
            mList.clear();
            mList.addAll(data2);
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.addData(data2);
        }

    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        mPresenter.getMyConcernData(mId, mType, page);
    }
}
