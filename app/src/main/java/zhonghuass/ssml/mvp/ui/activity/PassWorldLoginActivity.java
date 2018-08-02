package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerPassWorldLoginComponent;
import zhonghuass.ssml.di.module.PassWorldLoginModule;
import zhonghuass.ssml.mvp.contract.PassWorldLoginContract;
import zhonghuass.ssml.mvp.presenter.PassWorldLoginPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class PassWorldLoginActivity extends MBaseActivity<PassWorldLoginPresenter> implements PassWorldLoginContract.View {

    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_key)
    EditText edtKey;
    @BindView(R.id.iv_passworld_choose)
    ImageView ivPassworldChoose;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.tv_upload)
    TextView tvUpload;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPassWorldLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .passWorldLoginModule(new PassWorldLoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login_password; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        llTop.setVisibility(View.GONE);
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

    @OnClick({R.id.iv_passworld_choose, R.id.tv_forget, R.id.tv_upload, R.id.tv_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_passworld_choose:
                break;
            case R.id.tv_forget:
                ArmsUtils.startActivity(ForgetPassworldActivity.class);
                break;
            case R.id.tv_upload:
                pwtoLogin();
                break;
            case R.id.tv_agreement:
                break;
        }
    }

    private void pwtoLogin() {
        String mPhone = edtPhone.getText().toString().trim();
        String mPassworld = edtKey.getText().toString().trim();
        if (TextUtils.isEmpty(mPhone)) {
            ArmsUtils.makeText(this, "请输入手机号码!");
            return;
        }
        if (TextUtils.isEmpty(mPassworld)) {
            ArmsUtils.makeText(this, "请核输入密码");
        }
        mPresenter.pwtoLogin(mPhone, mPassworld);
    }
}
