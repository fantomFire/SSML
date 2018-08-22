package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerMyCollectionComponent;
import zhonghuass.ssml.di.module.MyCollectionModule;
import zhonghuass.ssml.mvp.contract.MyCollectionContract;
import zhonghuass.ssml.mvp.model.appbean.CollectionBean;
import zhonghuass.ssml.mvp.presenter.MyCollectionPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.utils.CustomPopupWindow;
import zhonghuass.ssml.utils.CustomProgress;
import zhonghuass.ssml.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MyCollectionActivity extends MBaseActivity<MyCollectionPresenter> implements MyCollectionContract.View, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.srl_collection)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rv_collection)
    RecyclerView recyclerView;
    private List<CollectionBean> mList = new ArrayList<>();
    private BaseQuickAdapter<CollectionBean, BaseViewHolder> mAdapter;
    private String mId = "1";
    private String mType = "1";
    private int page = 1;
    private CustomPopupWindow popupWindow;
    private int tempPosition = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyCollectionComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myCollectionModule(new MyCollectionModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_collection; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        initToolBar("我的收藏");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BaseQuickAdapter<CollectionBean, BaseViewHolder>(R.layout.item_my_collection, mList) {
            @Override
            protected void convert(BaseViewHolder helper, final CollectionBean item) {

                helper.setText(R.id.tv_name, item.getMember_name())
                        .setText(R.id.tv_time, item.getAdd_time())
                        .setText(R.id.tv_title, item.getTheme_title())
                        .setText(R.id.tv_content, item.getContent_title())
                        .setText(R.id.tv_num_reading, item.getAmount_of_reading())
                        .setText(R.id.tv_num_praise, item.getAmount_of_praise())
                        .setText(R.id.tv_num_comment, item.getAmount_of_comment())
                        .setText(R.id.tv_num_forward, item.getAmount_of_forward());
                helper.setGone(R.id.tv_title, item.getTheme_title().equals("") ? false : true);
                GlideUtils.intoDefault(getApplicationContext(), item.getMember_image(), (ImageView) helper.getView(R.id.civ_photo));
                GlideUtils.intoDefault(getApplicationContext(), item.getContent_cover(), (ImageView) helper.getView(R.id.siv_content));
                ImageView ivMenu = helper.getView(R.id.iv_menu);
                ivMenu.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        tempPosition = getParentPosition(item);
                        popupWindow = new CustomPopupWindow(getApplicationContext(), R.layout.popupwindow_layout, 100, 100);
                        popupWindow.showAtLocation(ivMenu, 0, 5);
                        popupWindow.tvConcern.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPresenter.toConcern(mId, mType, item.getMember_id(), item.getMember_type());
                            }
                        });
                        popupWindow.tvDel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPresenter.toCancelCollection(mId, mType, item.getContent_id());
                            }
                        });
                    }
                });
            }
        };
        mAdapter.setOnLoadMoreListener(this);
        recyclerView.setAdapter(mAdapter);

        mPresenter.getMyCollection(new CustomProgress(this), mId, mType, page);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
                page = 1;
                mPresenter.getMyCollection(new CustomProgress(MyCollectionActivity.this), mId, mType, page);
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
    public void showData(List<CollectionBean> data) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        mAdapter.setEnableLoadMore(true);
        mAdapter.loadMoreComplete();


        if (page == 1) {
            mAdapter.setNewData(data);
        } else {
            mAdapter.addData(data);
        }
    }

    @Override
    public void showNoData() {
        mAdapter.loadMoreEnd();
    }

    @Override
    public void showConcernSuccess(String message) {
        showMessage(message);
    }

    @Override
    public void showCancelCollectionSuccess(String message) {
        showMessage(message);
        mAdapter.remove(tempPosition);
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        mPresenter.getMyCollection(new CustomProgress(this), mId, mType, page);
    }
}
