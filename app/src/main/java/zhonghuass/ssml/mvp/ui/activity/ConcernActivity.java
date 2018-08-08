package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.library.baseAdapter.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerConcernComponent;
import zhonghuass.ssml.di.module.ConcernModule;
import zhonghuass.ssml.mvp.contract.ConcernContract;
import zhonghuass.ssml.mvp.model.appbean.ShareMeBean;
import zhonghuass.ssml.mvp.presenter.ConcernPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.mvp.ui.adapter.ConcernAdapter;
import zhonghuass.ssml.mvp.ui.adapter.ShareMeAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 私信--与我相关--关注我的页面
 */
public class ConcernActivity extends MBaseActivity<ConcernPresenter> implements ConcernContract.View ,BaseQuickAdapter.RequestLoadMoreListener{

    @BindView(R.id.rv_concern)
    RecyclerView rvConcern;
    private String member_id="1";
    private String member_type="0";
    private int page;
    private List<ShareMeBean> mList=new ArrayList<>();
    private ConcernAdapter concernAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerConcernComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .concernModule(new ConcernModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_concern; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolBar("关注我");
        rvConcern.setLayoutManager(new LinearLayoutManager(this));
        concernAdapter = new ConcernAdapter(R.layout.share_me_item, mList);
        concernAdapter.setOnLoadMoreListener(this);
        rvConcern.setAdapter(concernAdapter);
        mPresenter.getConcernData(member_id,member_type,page);


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
    public void showConcernData(List<ShareMeBean> data) {
        if (page>1){
            concernAdapter.addData(data);
        }else {
            mList.clear();
            mList.addAll(data);
        }
        concernAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        mPresenter.getConcernData(member_id,member_type,page);
    }
}
