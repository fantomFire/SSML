package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import butterknife.BindView;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerPostVideosComponent;
import zhonghuass.ssml.di.module.PostVideosModule;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.PostVideosContract;
import zhonghuass.ssml.mvp.model.appbean.IniviteBean;
import zhonghuass.ssml.mvp.presenter.PostVideosPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.mvp.ui.adapter.PostVideoAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class PostVideosActivity extends MBaseActivity<PostVideosPresenter> implements PostVideosContract.View {

    @BindView(R.id.rvGrid)
    RecyclerView rvGrid;
    private List<IniviteBean.ListBean> list;
    private PostVideoAdapter adapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPostVideosComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .postVideosModule(new PostVideosModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_post_videos; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("发布");
        rvGrid.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new PostVideoAdapter(R.layout.postvideos_item, list);
        rvGrid.setAdapter(adapter);
        rvGrid.setNestedScrollingEnabled(false);
        mPresenter.getInviteData("1", 1, 5);
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
    public void showdata(BaseResponse<IniviteBean> iniviteBeanBaseResponse) {
        list = iniviteBeanBaseResponse.getData().list;
        adapter.addData(list);
    }
}
