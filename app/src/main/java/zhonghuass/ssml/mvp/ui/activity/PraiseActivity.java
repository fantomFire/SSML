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
import zhonghuass.ssml.di.component.DaggerPraiseComponent;
import zhonghuass.ssml.di.module.PraiseModule;
import zhonghuass.ssml.mvp.contract.PraiseContract;
import zhonghuass.ssml.mvp.model.appbean.ShareMeBean;
import zhonghuass.ssml.mvp.presenter.PraisePresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.mvp.ui.adapter.ConcernAdapter;
import zhonghuass.ssml.mvp.ui.adapter.PraiseAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 消息--与我相关--赞我的页面
 */
public class PraiseActivity extends MBaseActivity<PraisePresenter> implements PraiseContract.View {

    @BindView(R.id.rv_praise)
    RecyclerView rvPraise;
    private String member_id="1";
    private String member_type="0";
    private int page;
    private List<ShareMeBean> mList=new ArrayList<>();
    private PraiseAdapter praiseAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPraiseComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .praiseModule(new PraiseModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_praise; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolBar("赞我");
        rvPraise.setLayoutManager(new LinearLayoutManager(this));
        praiseAdapter = new PraiseAdapter(R.layout.share_me_item, mList);
        rvPraise.setAdapter(praiseAdapter);
        mPresenter.getPraiseData(member_id,member_type,page);
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
    public void showPraiseData(List<ShareMeBean> data) {
        mList.clear();
        mList.addAll(data);
        praiseAdapter.notifyDataSetChanged();
    }
}
