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

import zhonghuass.ssml.di.component.DaggerCompanyRecommendComponent;
import zhonghuass.ssml.di.module.CompanyRecommendModule;
import zhonghuass.ssml.mvp.contract.CompanyRecommendContract;
import zhonghuass.ssml.mvp.presenter.CompanyRecommendPresenter;

import zhonghuass.ssml.R;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class CompanyRecommendFragment extends BaseFragment<CompanyRecommendPresenter> implements CompanyRecommendContract.View {

    public static CompanyRecommendFragment newInstance() {
        CompanyRecommendFragment fragment = new CompanyRecommendFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCompanyRecommendComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .companyRecommendModule(new CompanyRecommendModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_company_recommend, container, false);
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
