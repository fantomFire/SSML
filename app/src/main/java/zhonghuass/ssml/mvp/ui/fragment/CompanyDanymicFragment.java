package zhonghuass.ssml.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerCompanyDanymicComponent;
import zhonghuass.ssml.di.module.CompanyDanymicModule;
import zhonghuass.ssml.mvp.contract.CompanyDanymicContract;
import zhonghuass.ssml.mvp.model.appbean.DanynimicBean;
import zhonghuass.ssml.mvp.presenter.CompanyDanymicPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class CompanyDanymicFragment extends BaseFragment<CompanyDanymicPresenter> implements CompanyDanymicContract.View {


    @BindView(R.id.tv_photo)
    TextView tvPhoto;
    @BindView(R.id.tv_av)
    TextView tvAv;
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    private PhotoFragment fragmentPhoto;
    private VideoFragment videoFragment;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    public static CompanyDanymicFragment newInstance() {
        CompanyDanymicFragment fragment = new CompanyDanymicFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCompanyDanymicComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .companyDanymicModule(new CompanyDanymicModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_company_danymic, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        fragmentPhoto = PhotoFragment.newInstance();
        videoFragment = VideoFragment.newInstance();

        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragmentPhoto);
        fragmentTransaction.commit();

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


    @Override
    public void setContent(List<DanynimicBean> listDamnymic) {

    }

    @OnClick({R.id.tv_photo, R.id.tv_av})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_photo:
                chooseColor(tvPhoto, tvAv);
                tvPhoto.setBackground(getResources().getDrawable(R.drawable.company_danymic));
                tvAv.setBackground(getResources().getDrawable(R.drawable.company_danymic_right_gray));
                FragmentManager fm1 = getActivity().getSupportFragmentManager();
                fm1.beginTransaction().replace(R.id.frame_layout, fragmentPhoto).commit();

                break;
            case R.id.tv_av:
                chooseColor(tvAv, tvPhoto);
                tvPhoto.setBackground(getResources().getDrawable(R.drawable.company_danymic_gray));
                tvAv.setBackground(getResources().getDrawable(R.drawable.company_danymic_right));
                FragmentManager fm2 = getActivity().getSupportFragmentManager();
                fm2.beginTransaction().replace(R.id.frame_layout, videoFragment).commit();
                break;
        }
    }

    private void chooseColor(TextView tvPhoto, TextView tvAv) {
        tvPhoto.setTextColor(Color.parseColor("#cf1313"));
        tvAv.setTextColor(Color.parseColor("#666666"));

    }

}
