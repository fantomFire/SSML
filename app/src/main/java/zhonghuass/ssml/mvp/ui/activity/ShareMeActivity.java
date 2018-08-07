package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
import zhonghuass.ssml.mvp.ui.adapter.TradeAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 消息--与我相关--分享我的页面
 */

public class ShareMeActivity extends MBaseActivity<ShareMePresenter> implements ShareMeContract.View {

    @BindView(R.id.rv_share_me)
    RecyclerView rvShareMe;
    private ShareMeAdapter shareMeAdapter;
    private List<ShareMeBean> mList= new ArrayList<>();
    private String member_id="1";
    private String member_type="0";
    private int page=1;

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
        mPresenter.getShareMeData(member_id,member_type,page);
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
        System.out.println("===="+data.get(0).getMember_name());
        // tradeAdapter.addData(data);
        mList.clear();
        mList.addAll(data);
        shareMeAdapter.notifyDataSetChanged();
    }
}
