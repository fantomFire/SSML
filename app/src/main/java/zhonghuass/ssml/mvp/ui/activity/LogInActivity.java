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
import com.maning.mndialoglibrary.MProgressDialog;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerLogInComponent;
import zhonghuass.ssml.di.module.LogInModule;
import zhonghuass.ssml.mvp.contract.LogInContract;
import zhonghuass.ssml.mvp.model.appbean.LoginBean;
import zhonghuass.ssml.mvp.presenter.LogInPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.utils.Constants;
import zhonghuass.ssml.utils.PrefUtils;


public class LogInActivity extends MBaseActivity<LogInPresenter> implements LogInContract.View {

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
    @BindView(R.id.iv_weixin)
    ImageView ivWeixin;
    @BindView(R.id.iv_qq)
    ImageView ivQq;
    @BindView(R.id.iv_weibo)
    ImageView ivWeibo;
    @BindView(R.id.iv_tip_choose)
    ImageView ivTipChoose;
    private Disposable mDispos;
    private boolean isflag;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLogInComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .logInModule(new LogInModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_log_in; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0

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
        MProgressDialog.dismissProgress();
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }


    @Override
    public void killMyself() {
        finish();
    }


    @OnClick({R.id.tv_getcode, R.id.tv_register, R.id.tv_passworld_login, R.id.tv_upload, R.id.tv_enter, R.id.tv_agreement, R.id.iv_weixin, R.id.iv_qq, R.id.iv_weibo, R.id.iv_tip_choose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_getcode:
                getLoginCode();
                break;
            case R.id.tv_register://注册
                startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
                break;
            case R.id.tv_passworld_login://密码登录
                ArmsUtils.startActivity(PassWorldLoginActivity.class);
                break;
            case R.id.tv_upload:
//                ArmsUtils.startActivity(MainActivity.class);
                toLogin();
                break;
            case R.id.tv_enter://企业登录
                ArmsUtils.startActivity(EnterpriseLoginActivity.class);
                break;
            case R.id.tv_agreement:
                break;
            case R.id.iv_weixin:
                break;
            case R.id.iv_qq:
                break;
            case R.id.iv_weibo:
                break;
            case R.id.iv_tip_choose:
                toAgreement();
                break;
        }
    }

    private void getLoginCode() {
        String mPhone = edtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(mPhone)) {
            ArmsUtils.makeText(this, "请核对手机号码!");
            return;
        }
        tvGetcode.setEnabled(false);
        mPresenter.getCode(mPhone);
        mDispos = Flowable.interval(1, 1, TimeUnit.SECONDS)
                .take(60)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext((aLong) -> {
                    tvGetcode.setText("重新获取(" + (60 - aLong) + ")");
                })
                .doOnComplete(() -> {
                    tvGetcode.setEnabled(true);
                    tvGetcode.setText("获取验证码");
                })
                .doOnError((throwable) ->
                        throwable.printStackTrace()
                )
                .subscribe();

    }

    private void toLogin() {
        String mPhone = edtPhone.getText().toString().trim();
        String mCode = edtCode.getText().toString().trim();
        if (TextUtils.isEmpty(mPhone)) {
            ArmsUtils.makeText(this, "请输入手机号码！");
            return;
        }
        if (TextUtils.isEmpty(mCode)) {
            ArmsUtils.makeText(this, "请输入手机号验证码！");
            return;
        }
        mPresenter.toLogin(mPhone, mCode);

    }

    private void toAgreement() {
        if (isflag) {
            ivTipChoose.setBackgroundResource(R.mipmap.login_icon_5);
            isflag = false;
        } else {
            ivTipChoose.setBackgroundResource(R.mipmap.login_icon_5_1);
            isflag = true;
        }
    }


    @Override
    public void gotoActivity(LoginBean voidBaseResponse) {
        PrefUtils.putString(LogInActivity.this, Constants.USER_ID, voidBaseResponse.data.uid);
        ArmsUtils.startActivity(MainActivity.class);
        LogInActivity.this.finish();
    }
}
