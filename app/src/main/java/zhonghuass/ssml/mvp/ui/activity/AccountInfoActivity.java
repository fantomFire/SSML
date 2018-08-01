package zhonghuass.ssml.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.ui.MBaseActivity;

public class AccountInfoActivity extends MBaseActivity {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_account_info;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
    }
}
