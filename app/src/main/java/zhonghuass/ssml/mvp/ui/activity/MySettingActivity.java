package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.OnClick;
import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.mvp.ui.activity.di.component.DaggerMySettingActivityComponent;
import zhonghuass.ssml.mvp.model.MySettingActivityModule;
import zhonghuass.ssml.mvp.contract.MySettingActivityContract;
import zhonghuass.ssml.mvp.presenter.MySettingActivityPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MySettingActivity extends MBaseActivity<MySettingActivityPresenter> implements MySettingActivityContract.View {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_account_info)
    LinearLayout llAccountInfo;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMySettingActivityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mySettingActivityModule(new MySettingActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_setting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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


    @OnClick({R.id.ll_account_info, R.id.ll_update})
    public void onViewClicked(View view) {
        super.onViewClicked(view);
        switch (view.getId()) {
            case R.id.ll_account_info:
                ArmsUtils.startActivity(MyAccountInfoActivity.class);
                break;
            case R.id.ll_update:
                ArmsUtils.startActivity(MyAppUpdateActivity.class);
                break;
        }
    }
}
