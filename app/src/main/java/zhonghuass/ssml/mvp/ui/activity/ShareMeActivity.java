package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.library.baseAdapter.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerShareMeComponent;
import zhonghuass.ssml.di.module.ShareMeModule;
import zhonghuass.ssml.mvp.contract.ShareMeContract;
import zhonghuass.ssml.mvp.model.appbean.ShareMeBean;
import zhonghuass.ssml.mvp.presenter.ShareMePresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.mvp.ui.adapter.ShareMeAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 消息--与我相关--分享我的页面
 */

public class ShareMeActivity extends MBaseActivity<ShareMePresenter> implements ShareMeContract.View ,
        BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_share_me)
    RecyclerView rvShareMe;
    @BindView(R.id.sr_share_me)
    SwipeRefreshLayout srShareMe;
    private ShareMeAdapter shareMeAdapter;
    private List<ShareMeBean> mList = new ArrayList<>();
    private String member_id = "1";
    private String member_type = "0";
    private int page = 1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerShareMeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .shareMeModule(new ShareMeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_share_me; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolBar("分享我");
        rvShareMe.setLayoutManager(new LinearLayoutManager(this));
        shareMeAdapter = new ShareMeAdapter(R.layout.share_me_item, mList);
        rvShareMe.setAdapter(shareMeAdapter);
        mPresenter.getShareMeData(member_id, member_type, page);
        shareMeAdapter.setOnLoadMoreListener(this);
        // 设置颜色属性的时候一定要注意是引用了资源文件还是直接设置16进制的颜色，因为都是int值容易搞混
        // 设置下拉进度的背景颜色，默认就是白色的
        srShareMe.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        srShareMe.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);

        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        srShareMe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // 开始刷新，设置当前为刷新状态
//                srConcern.setRefreshing(true);
                shareMeAdapter.setEnableLoadMore(true);

                // 这里是主线程
                // 一些比较耗时的操作，比如联网获取数据，需要放到子线程去执行
                // TODO 获取数据
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        mPresenter.getShareMeData(member_id, member_type, page);
                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                    }
                }, 800);


                // 这个不能写在外边，不然会直接收起来
                //swipeRefreshLayout.setRefreshing(false);
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
    public void showShareMeData(List<ShareMeBean> data) {
        shareMeAdapter.setEnableLoadMore(true);
        if (srShareMe.isRefreshing()) {
            srShareMe.setRefreshing(false);
        }

        if (data.size() > 0) {
            //当前环节数据加载完毕
            shareMeAdapter.loadMoreComplete();
        } else {
            //没有更多数据
            shareMeAdapter.loadMoreEnd();
            shareMeAdapter.disableLoadMoreIfNotFullPage(rvShareMe);
            return;
        }
        if (page > 1) {
            shareMeAdapter.addData(data);
        } else {
            mList.clear();
            mList.addAll(data);
            shareMeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        mPresenter.getShareMeData(member_id, member_type, page);
    }
}
