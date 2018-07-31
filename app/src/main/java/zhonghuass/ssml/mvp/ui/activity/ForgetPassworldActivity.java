package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.OnClick;
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


    @OnClick({R.id.tv_getcode, R.id.tv_upload, R.id.tv_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_getcode:
                break;
            case R.id.tv_upload:
                ArmsUtils.startActivity(ConfirModiActivity.class);
                break;
            case R.id.tv_agreement:
                break;
        }
    }
}
