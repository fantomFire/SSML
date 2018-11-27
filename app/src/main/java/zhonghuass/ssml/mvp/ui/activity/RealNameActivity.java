package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerRealNameActivityComponent;
import zhonghuass.ssml.di.module.RealNameActivityModule;
import zhonghuass.ssml.mvp.contract.RealNameActivityContract;
import zhonghuass.ssml.mvp.presenter.RealNameActivityPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.utils.Constants;
import zhonghuass.ssml.utils.PrefUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class RealNameActivity extends MBaseActivity<RealNameActivityPresenter> implements RealNameActivityContract.View {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.et_cid)
    EditText etCid;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    private String user_id;
    private String memberType;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRealNameActivityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .realNameActivityModule(new RealNameActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_real_name; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        user_id = PrefUtils.getString(this, Constants.USER_ID, "");
        memberType = PrefUtils.getString(this, Constants.MEMBER_TYPE, "0");
        initToolBar("实名认证");
    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.btn_commit:
                postInfo();
                break;

        }

    }

    private void postInfo() {
        String eName = etName.getText().toString().trim();
        String eTel = etTel.getText().toString().trim();
        String eCid = etCid.getText().toString().trim();

        if(TextUtils.isEmpty(eName)){
            showMessage("请核对用户名!");
            return;
        }
        if(TextUtils.isEmpty(eTel)){
            showMessage("请核对电话!");
            return;
        }
        if(TextUtils.isEmpty(eCid)){
            showMessage("请核对身份证信息!");
            return;
        }
        System.out.println("user_id"+user_id+"memberType"+memberType);
        System.out.println("eName"+eName+"eTel"+eTel+"eCid"+eCid);
        mPresenter.postUserInfo(user_id,memberType,eName,eTel,eCid);
    }

    @Override
    public void killMyself() {
        finish();
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
    public void changeState() {

    }
}
