package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import zhonghuass.ssml.di.component.DaggerConfirModiComponent;
import zhonghuass.ssml.di.module.ConfirModiModule;
import zhonghuass.ssml.mvp.contract.ConfirModiContract;
import zhonghuass.ssml.mvp.presenter.ConfirModiPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class ConfirModiActivity extends MBaseActivity<ConfirModiPresenter> implements ConfirModiContract.View {

    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_passworld)
    EditText edtPassworld;
    @BindView(R.id.tv_upload)
    TextView tvUpload;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.tv_passworld_new)
    ImageView tvPassworldNew;
    @BindView(R.id.tv_passworld_old)
    ImageView tvPassworldOld;
    @BindView(R.id.iv_tip_choose)
    ImageView ivTipChoose;
    private boolean isChecked;
    private boolean isChecked2;
    private boolean isflag;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerConfirModiComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .confirModiModule(new ConfirModiModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_confir_modi; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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

    @OnClick({R.id.tv_upload, R.id.tv_agreement, R.id.tv_passworld_new, R.id.tv_passworld_old, R.id.iv_tip_choose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_upload:
                toConfirModi();
                break;
            case R.id.tv_agreement:
                break;
            case R.id.tv_passworld_new:
                toChoosenew();
                break;
            case R.id.tv_passworld_old:
                toChooseold();
                break;
            case R.id.iv_tip_choose:
                toAgreement();
                break;
        }
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


    private void toChoosenew() {
        if (isChecked) {
            //如果选中，显示密码
            edtPhone.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isChecked = false;
        } else {
            //否则隐藏密码
            edtPhone.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isChecked = true;
        }
    }

    private void toChooseold() {
        if (isChecked2) {
            //如果选中，显示密码
            edtPassworld.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isChecked2 = false;
        } else {
            //否则隐藏密码
            edtPassworld.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isChecked2 = true;
        }
    }

    private void toConfirModi() {
        Intent intent = getIntent();
        String phone = intent.getStringExtra("forgetmPhone");
        String code = intent.getStringExtra("forgetmmCode");
        String newpw = edtPhone.getText().toString().trim();
        String oldpw = edtPassworld.getText().toString().trim();
        mPresenter.toConfirModi(phone, code, newpw, oldpw);

    }

    @Override
    public void toNewActivity() {
        ArmsUtils.startActivity(LogInActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}
