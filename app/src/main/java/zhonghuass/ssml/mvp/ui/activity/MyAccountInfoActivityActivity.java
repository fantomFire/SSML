package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerMyAccountInfoActivityComponent;
import zhonghuass.ssml.di.module.MyAccountInfoActivityModule;
import zhonghuass.ssml.mvp.contract.MyAccountInfoActivityContract;
import zhonghuass.ssml.mvp.presenter.MyAccountInfoActivityPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MyAccountInfoActivityActivity extends MBaseActivity<MyAccountInfoActivityPresenter> implements MyAccountInfoActivityContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyAccountInfoActivityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myAccountInfoActivityModule(new MyAccountInfoActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_account_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
