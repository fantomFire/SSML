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
import zhonghuass.ssml.di.component.DaggerMessageListComponent;
import zhonghuass.ssml.di.module.MessageListModule;
import zhonghuass.ssml.mvp.contract.MessageListContract;
import zhonghuass.ssml.mvp.model.appbean.MessageListBean;
import zhonghuass.ssml.mvp.presenter.MessageListPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.mvp.ui.adapter.MessageListAdapter;
import zhonghuass.ssml.mvp.ui.adapter.ShareMeAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 消息-与我相关--私信我的页面
 */
public class MessageListActivity extends MBaseActivity<MessageListPresenter> implements MessageListContract.View,
        BaseQuickAdapter.RequestLoadMoreListener{

    @BindView(R.id.rv_message_list)
    RecyclerView rvMessageList;
    @BindView(R.id.sr_message_list)
    SwipeRefreshLayout srMessageList;
    private List<MessageListBean> mList=new ArrayList<>();
    private MessageListAdapter messageListAdapter;
    private String member_id="1";
    private String member_type="0";
    private int page=0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMessageListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .messageListModule(new MessageListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_message_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolBar("私信");
        rvMessageList.setLayoutManager(new LinearLayoutManager(this));
        messageListAdapter = new MessageListAdapter(R.layout.message_list_item, mList);
        rvMessageList.setAdapter(messageListAdapter);
        mPresenter.getMessageListData(member_id, member_type, page);
        messageListAdapter.setOnLoadMoreListener(this);
        // 设置颜色属性的时候一定要注意是引用了资源文件还是直接设置16进制的颜色，因为都是int值容易搞混
        // 设置下拉进度的背景颜色，默认就是白色的
        srMessageList.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        srMessageList.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);

        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        srMessageList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // 开始刷新，设置当前为刷新状态
//                srConcern.setRefreshing(true);
                messageListAdapter.setEnableLoadMore(true);

                // 这里是主线程
                // 一些比较耗时的操作，比如联网获取数据，需要放到子线程去执行
                // TODO 获取数据
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        mPresenter.getMessageListData(member_id, member_type, page);
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
    public void onLoadMoreRequested() {
        page++;
        mPresenter.getMessageListData(member_id, member_type, page);
    }

    @Override
    public void showMessageListData(List<MessageListBean> data) {
        messageListAdapter.setEnableLoadMore(true);
        if (srMessageList.isRefreshing()) {
            srMessageList.setRefreshing(false);
        }

        if (data.size() > 0) {
            //当前环节数据加载完毕
            messageListAdapter.loadMoreComplete();
        } else {
            //没有更多数据
            messageListAdapter.loadMoreEnd();
            messageListAdapter.disableLoadMoreIfNotFullPage(rvMessageList);
            return;
        }
        if (page > 1) {
            messageListAdapter.addData(data);
        } else {
            mList.clear();
            mList.addAll(data);
            messageListAdapter.notifyDataSetChanged();
        }
    }
}
