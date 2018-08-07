package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import zhonghuass.ssml.di.component.DaggerMessageListComponent;
import zhonghuass.ssml.di.module.MessageListModule;
import zhonghuass.ssml.mvp.contract.MessageListContract;
import zhonghuass.ssml.mvp.presenter.MessageListPresenter;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.ui.MBaseActivity;


import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 消息-与我相关--私信我的页面
 */
public class MessageListActivity extends MBaseActivity<MessageListPresenter> implements MessageListContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMessageListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .messageListModule(new MessageListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_message_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
