package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerFeedBackActivityComponent;
import zhonghuass.ssml.di.module.FeedBackActivityModule;
import zhonghuass.ssml.mvp.contract.FeedBackActivityContract;
import zhonghuass.ssml.mvp.presenter.FeedBackActivityPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.utils.Constants;
import zhonghuass.ssml.utils.PrefUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class FeedBackActivityActivity extends MBaseActivity<FeedBackActivityPresenter> implements FeedBackActivityContract.View {

    @BindView(R.id.et_context)
    EditText etContext;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    private String user_id;
    private String memberType;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFeedBackActivityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .feedBackActivityModule(new FeedBackActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_feed_back; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        user_id = PrefUtils.getString(this, Constants.USER_ID, "");
        memberType = PrefUtils.getString(this, Constants.MEMBER_TYPE, "1");
    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        String eContext = etContext.getText().toString().trim();
        if(TextUtils.isEmpty(eContext)){
            showMessage("请核对用户名!");
            return;
        }
        mPresenter.postMess(user_id,memberType,eContext);
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
        ArmsUtils.makeText(this,message);
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
    public void clearText() {
        etContext.setText("");
    }
}
