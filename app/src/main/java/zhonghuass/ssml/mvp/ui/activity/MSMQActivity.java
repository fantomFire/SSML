package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import zhonghuass.ssml.di.component.DaggerMSMQComponent;
import zhonghuass.ssml.di.module.MSMQModule;
import zhonghuass.ssml.mvp.contract.MSMQContract;
import zhonghuass.ssml.mvp.presenter.MSMQPresenter;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.ui.MBaseActivity;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MSMQActivity extends MBaseActivity<MSMQPresenter> implements MSMQContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMSMQComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mSMQModule(new MSMQModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_msmq; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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
}
