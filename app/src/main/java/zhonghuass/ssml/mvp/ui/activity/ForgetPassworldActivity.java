package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerForgetPassworldComponent;
import zhonghuass.ssml.di.module.ForgetPassworldModule;
import zhonghuass.ssml.mvp.contract.ForgetPassworldContract;
import zhonghuass.ssml.mvp.presenter.ForgetPassworldPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ForgetPassworldActivity extends MBaseActivity<ForgetPassworldPresenter> implements ForgetPassworldContract.View {

    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.tv_getcode)
    TextView tvGetcode;
    @BindView(R.id.tv_upload)
    TextView tvUpload;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.iv_tip_choose)
    ImageView ivTipChoose;
    @BindView(R.id.ll_tip_choose)
    LinearLayout llTipChoose;
    private Disposable mDispos;
    private boolean isflag=true;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerForgetPassworldComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .forgetPassworldModule(new ForgetPassworldModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_forgetpassworld; //如果你不需要框架帮你设置 setContentView(id) 需要 自行设置,请返回 0
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


    @OnClick({R.id.tv_getcode, R.id.tv_upload, R.id.tv_agreement, R.id.ll_tip_choose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_getcode://获取验证码
                togetCode();
                break;
            case R.id.tv_upload:
                toForgetPassworldVerification();
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

    private void toForgetPassworldVerification() {
        String mPhone = edtPhone.getText().toString().trim();
        String mCode = edtCode.getText().toString().trim();
        if (isflag==false){
            Toast.makeText(this, "请阅读用户协议，并确认勾选协议", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(mPhone)) {
            ArmsUtils.makeText(this, "请输入手机号码!");
            return;
        }
        if (TextUtils.isEmpty(mCode)) {
            ArmsUtils.makeText(this, "请输入验证码！");
            return;
        }
        mPresenter.toForgetPassworldVerification(mPhone, mCode);

    }

    private void togetCode() {
        String mPhone = edtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(mPhone)) {
            ArmsUtils.makeText(this, "请输入手机号码!");
            return;
        }
        //验证码倒计时
        tvGetcode.setEnabled(false);
        mPresenter.togetCode(mPhone);
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

    @Override
    public void toNewActivity() {
        String mPhone = edtPhone.getText().toString().trim();
        String mCode = edtCode.getText().toString().trim();
        Intent intent = new Intent(ForgetPassworldActivity.this, ConfirModiActivity.class);
        intent.putExtra("forgetmPhone", mPhone);
        intent.putExtra("forgetmmCode", mCode);
        ArmsUtils.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDispos != null) {
            mDispos.dispose();
        }
    }
}
