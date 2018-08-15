package zhonghuass.ssml.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import zhonghuass.ssml.di.component.DaggerCompanyBriefComponent;
import zhonghuass.ssml.di.module.CompanyBriefModule;
import zhonghuass.ssml.mvp.contract.CompanyBriefContract;
import zhonghuass.ssml.mvp.presenter.CompanyBriefPresenter;

import zhonghuass.ssml.R;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class CompanyBriefFragment extends BaseFragment<CompanyBriefPresenter> implements CompanyBriefContract.View {

    public static CompanyBriefFragment newInstance() {
        CompanyBriefFragment fragment = new CompanyBriefFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCompanyBriefComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .companyBriefModule(new CompanyBriefModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_company_brief, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }


    @Override
    public void setData(@Nullable Object data) {

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

    }
}
