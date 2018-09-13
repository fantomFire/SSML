package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerWeiXinLoginComponent;
import zhonghuass.ssml.di.module.WeiXinLoginModule;
import zhonghuass.ssml.mvp.contract.WeiXinLoginContract;
import zhonghuass.ssml.mvp.presenter.WeiXinLoginPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class WeiXinLoginActivity extends BaseActivity<WeiXinLoginPresenter> implements WeiXinLoginContract.View {


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWeiXinLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .weiXinLoginModule(new WeiXinLoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_wei_xin_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
