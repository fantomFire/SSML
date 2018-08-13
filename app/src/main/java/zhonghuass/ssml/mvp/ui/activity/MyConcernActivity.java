package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import com.github.library.baseAdapter.BaseQuickAdapter;
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
import zhonghuass.ssml.mvp.ui.adapter.MyConcernAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 我的关注页面
 */
public class MyConcernActivity extends MBaseActivity<MyConcernPresenter> implements MyConcernContract.View, BaseQuickAdapter.RequestLoadMoreListener {
    private String mId = "1";
    private String mType = "1";
    private int page = 1;
    private List<ConcernFansBean> mList = new ArrayList<>();

    @BindView(R.id.rv_concern)
    RecyclerView rvConcern;
    @BindView(R.id.srl_concern)
    SwipeRefreshLayout swipeRefreshLayout;
    private MyConcernAdapter mAdapter;
    private boolean isCancel;
    private boolean isConcern;
    private int clickPosition = 0;
    private List<ConcernFansBean> mAdapterData;


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
        mAdapter = new MyConcernAdapter(R.layout.item_concern_fans, mList);
        mAdapter.setOnLoadMoreListener(this);

        rvConcern.setAdapter(mAdapter);
        mPresenter.getMyConcernData(mId, mType, page);


        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {


            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                clickPosition = position;
                switch (view.getId()) {
                    case R.id.tv_concern:
                        if (mAdapter.getData().get(position).mutual_concern.equals("2")) {
                            //关注操作
                            mPresenter.toConcern(mId, mType, mAdapter.getData().get(position).member_id, mAdapter.getData().get(position).member_type);
                        } else {
                            //取消关注操作
                            mPresenter.toCancelConcern(mId, mType, mAdapter.getData().get(position).member_id, mAdapter.getData().get(position).member_type);
                        }
                        break;
                    default:
                        break;
                }

            }
        });

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
            mAdapter.setNewData(data);
        } else {
            mAdapter.addData(data);
        }
        mAdapterData = mAdapter.getData();
        mAdapter.disableLoadMoreIfNotFullPage(rvConcern);
    }

    @Override
    public void showCancelSuccess(String message) {
        isCancel = true;
        // 取消成功返回
        showMessage(message);
        ConcernFansBean bean = mAdapter.getData().get(clickPosition);
        bean.mutual_concern_temp = bean.mutual_concern;
        bean.mutual_concern = "2";
        mAdapter.setData(clickPosition, bean);
    }

    @Override
    public void showConcernSuccess(String message) {
        isConcern = true;
        // 关注成功返回
        showMessage(message);
        // 去请求点击的item所在页的数据，准备更替刷新
        ConcernFansBean bean = mAdapterData.get(clickPosition);
        bean.mutual_concern = bean.mutual_concern_temp;
        mAdapter.setData(clickPosition, bean);
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        mPresenter.getMyConcernData(mId, mType, page);
    }
}
