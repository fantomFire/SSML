package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import zhonghuass.ssml.di.component.DaggerCommentComponent;
import zhonghuass.ssml.di.module.CommentModule;
import zhonghuass.ssml.mvp.contract.CommentContract;
import zhonghuass.ssml.mvp.presenter.CommentPresenter;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.ui.MBaseActivity;


import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 消息-与我相关--评论我的页面
 */
public class CommentActivity extends MBaseActivity<CommentPresenter> implements CommentContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCommentComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .commentModule(new CommentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_comment; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolBar("评论我");
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
