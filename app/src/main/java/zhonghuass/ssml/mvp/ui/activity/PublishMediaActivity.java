package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerPublishMediaComponent;
import zhonghuass.ssml.di.module.PublishMediaModule;
import zhonghuass.ssml.mvp.contract.PublishMediaContract;
import zhonghuass.ssml.mvp.presenter.PublishMediaPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class PublishMediaActivity extends BaseActivity<PublishMediaPresenter> implements PublishMediaContract.View {

    @BindView(R.id.mpaht)
    TextView mpaht;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPublishMediaComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .publishMediaModule(new PublishMediaModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_publish_media; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String mediaPath = getIntent().getStringExtra("mediaPath");
        mpaht.setText(mediaPath);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
