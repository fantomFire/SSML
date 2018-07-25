package zhonghuass.ssml.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.maning.mndialoglibrary.MProgressDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.contract.LogInContract;
import zhonghuass.ssml.mvp.presenter.LogInPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class LogInActivity extends BaseActivity<LogInPresenter> implements LogInContract.View {

    @BindView(R.id.tv_upload)
    TextView tvUpload;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.tv_getcode)
    TextView tvGetcode;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_passworld_login)
    TextView tvPassworldLogin;
    @BindView(R.id.tv_enter)
    TextView tvEnter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
//        DaggerLogInComponent //如找不到该类,请编译一下项目
//                .builder()
//                .appComponent(appComponent)
//                .logInModule(new LogInModule(this))
//                .build()
//                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_log_in; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        MProgressDialog.dismissProgress();
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

    @OnClick({R.id.tv_getcode, R.id.tv_register, R.id.tv_passworld_login, R.id.tv_upload, R.id.tv_enter, R.id.tv_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_getcode:
                break;
            case R.id.tv_register:
                startActivity(new Intent(LogInActivity.this,RegisterActivity.class));
                break;
            case R.id.tv_passworld_login:
//                ArmsUtils.startActivity(PassWorldLoginActivity.class);
                startActivity(new Intent(LogInActivity.this,PassWorldLoginActivity.class));
                break;
            case R.id.tv_upload:
                ArmsUtils.startActivity(MainActivity.class);
                break;
            case R.id.tv_enter:
                break;
            case R.id.tv_agreement:
                break;
        }
    }

}
