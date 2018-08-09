package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.github.library.baseAdapter.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerMyFansComponent;
import zhonghuass.ssml.di.module.MyFansModule;
import zhonghuass.ssml.mvp.contract.MyFansContract;
import zhonghuass.ssml.mvp.model.appbean.ConcernFansBean;
import zhonghuass.ssml.mvp.presenter.MyFansPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.mvp.ui.adapter.MyFansAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 我的粉丝页面
 */
public class MyFansActivity extends MBaseActivity<MyFansPresenter> implements MyFansContract.View, BaseQuickAdapter.RequestLoadMoreListener {

    private String mId = "1";
    private String mType = "1";
    private int page = 1;
    private List<ConcernFansBean> mList = new ArrayList<>();

    @BindView(R.id.rv_fans)
    RecyclerView rvFans;
    @BindView(R.id.srl_fans)
    SwipeRefreshLayout swipeRefreshLayout;
    private MyFansAdapter mAdapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyFansComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myFansModule(new MyFansModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_fans; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolBar("我的粉丝");
        rvFans.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyFansAdapter(R.layout.item_concern_fans, mList);
        mAdapter.setOnLoadMoreListener(this);

        rvFans.setAdapter(mAdapter);
        mPresenter.getMyFansDate(mId, mType, page);


        mAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                Log.e("--", "头像长按了" + "--" + position);
                return false;
            }
        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Log.e("--", "view点击了" + "--" + position);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
                page = 1;
                mPresenter.getMyFansDate(mId, mType, page);
            }
        });
    }

    @Override
    public void showDate(List<ConcernFansBean> data) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        mAdapter.setEnableLoadMore(true);

        if (data.size() > 0) {
            mAdapter.loadMoreComplete();
        } else {
            mAdapter.loadMoreEnd();
            return;
        }

        if (page == 1) {
//            mList.clear();
//            mList.addAll(data);
//            mAdapter.notifyDataSetChanged();
            mAdapter.setNewData(data);
        } else {
            mAdapter.addData(data);
        }
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
    public void onLoadMoreRequested() {
        page++;
        mPresenter.getMyFansDate(mId, mType, page);
    }
}
