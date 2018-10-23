package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerPassWorldLoginComponent;
import zhonghuass.ssml.di.module.PassWorldLoginModule;
import zhonghuass.ssml.mvp.contract.PassWorldLoginContract;
import zhonghuass.ssml.mvp.model.appbean.PWLoginBean;
import zhonghuass.ssml.mvp.presenter.PassWorldLoginPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.utils.Constants;
import zhonghuass.ssml.utils.PrefUtils;

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
    @BindView(R.id.iv_tip_choose)
    ImageView ivTipChoose;
    @BindView(R.id.ll_tip_choose)
    LinearLayout llTipChoose;
    private boolean isChecked;
    private boolean isflag = true;

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
        ivTipChoose.setBackgroundResource(R.mipmap.login_icon_5);
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
//        ArmsUtils.snackbarText(message);
        ArmsUtils.makeText(this, message);
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

    @OnClick({R.id.iv_passworld_choose, R.id.tv_forget, R.id.tv_upload, R.id.tv_agreement, R.id.ll_tip_choose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_passworld_choose:
                toChoose();
                break;
            case R.id.tv_forget:
                ArmsUtils.startActivity(ForgetPassworldActivity.class);
                break;
            case R.id.tv_upload:
                pwtoLogin();
                break;
            case R.id.tv_agreement:
                break;
            case R.id.ll_tip_choose:
                toAgreement();
                break;
        }
    }

    private void toAgreement() {
        if (isflag) {
            ivTipChoose.setBackgroundResource(R.mipmap.login_icon_5_1);
            isflag = false;
        } else {
            ivTipChoose.setBackgroundResource(R.mipmap.login_icon_5);
            isflag = true;
        }
    }

    private void toChoose() {
        if (isChecked) {
            //如果选中，显示密码
            edtKey.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ivPassworldChoose.setImageResource(R.mipmap.login_icon_8);
            isChecked = false;
        } else {
            //否则隐藏密码
            edtKey.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ivPassworldChoose.setImageResource(R.mipmap.login_icon_4);
            isChecked = true;
        }
    }

    private void pwtoLogin() {
        String mPhone = edtPhone.getText().toString().trim();
        String mPassworld = edtKey.getText().toString().trim();
        if (isflag == false) {
            Toast.makeText(this, "请阅读用户协议，并确认勾选协议", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mPhone)) {
            ArmsUtils.makeText(this, "请输入手机号码!");
            return;
        }
        if (TextUtils.isEmpty(mPassworld)) {
            ArmsUtils.makeText(this, "请核输入密码");
            return;
        }
        mPresenter.pwtoLogin(mPhone, mPassworld);
    }

    @Override
    public void gotoActivity(PWLoginBean voidBaseResponse) {
        PrefUtils.putString(PassWorldLoginActivity.this, Constants.MEMBER_TYPE, voidBaseResponse.data.member_type);
//        PrefUtils.putString(PassWorldLoginActivity.this, Constants.USER_ID, voidBaseResponse.data.uid);
        ArmsUtils.startActivity(MainActivity.class);
        PassWorldLoginActivity.this.finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
