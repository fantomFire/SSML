package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.OnClick;
import zhonghuass.ssml.di.component.DaggerHelpActivityComponent;
import zhonghuass.ssml.di.module.HelpActivityModule;
import zhonghuass.ssml.mvp.contract.HelpActivityContract;
import zhonghuass.ssml.mvp.presenter.HelpActivityPresenter;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.ui.MBaseActivity;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class HelpActivityActivity extends MBaseActivity<HelpActivityPresenter> implements HelpActivityContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHelpActivityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .helpActivityModule(new HelpActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_help; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolBar("帮助反馈");
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

    @OnClick({R.id.ll_real_name, R.id.ll_what_everyday, R.id.ll_edit_tel, R.id.tv_feedback})
    public void onViewClicked(View view) {
        super.onViewClicked(view);
        switch (view.getId()) {
            case R.id.ll_real_name:
                ArmsUtils.startActivity(HowToRealNameActivity.class);
                break;
            case R.id.ll_what_everyday:
                break;
            case R.id.ll_edit_tel:
                break;
            case R.id.tv_feedback:
                ArmsUtils.startActivity(FeedBackActivityActivity.class);
                break;
        }
    }
}
