package zhonghuass.ssml.mvp.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.library.pickerView.scrollPicker.CustomCityPicker;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.OnClick;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerMyInfoComponent;
import zhonghuass.ssml.di.module.MyInfoModule;
import zhonghuass.ssml.mvp.contract.MyInfoContract;
import zhonghuass.ssml.mvp.presenter.MyInfoPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MyInfoActivity extends MBaseActivity<MyInfoPresenter> implements MyInfoContract.View {

    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.et_about_my)
    EditText etMy;

    private CustomCityPicker cityPicker;
    private Dialog cityPickerDialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myInfoModule(new MyInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolBar("编辑资料", true, "保存");

        cityPicker = new CustomCityPicker(this, new CustomCityPicker.ResultHandler() {
            @Override
            public void handle(String result) {
                tvArea.setText(result);
            }
        });
        //提前初始化数据，这样可以加载快一些。
        cityPicker.initJson();

        etMy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("--", "输入：" + s);
                tvNum.setText(s.length() + "/140");
            }
        });
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


    @OnClick({R.id.ll_area, R.id.tv_right})
    public void onViewClicked(View view) {
        super.onViewClicked(view);
        switch (view.getId()) {
            case R.id.ll_area:
                cityPicker.show();
                break;
            case R.id.tv_right:
                break;
        }
    }
}
