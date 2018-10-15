package zhonghuass.ssml.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerCompanyBriefComponent;
import zhonghuass.ssml.di.module.CompanyBriefModule;
import zhonghuass.ssml.mvp.contract.CompanyBriefContract;
import zhonghuass.ssml.mvp.model.appbean.BriefBean;
import zhonghuass.ssml.mvp.presenter.CompanyBriefPresenter;
import zhonghuass.ssml.mvp.ui.adapter.LaurelAdapter;
import zhonghuass.ssml.utils.PrefUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class CompanyBriefFragment extends BaseFragment<CompanyBriefPresenter> implements CompanyBriefContract.View {

    @BindView(R.id.com_introduce)
    TextView comIntroduce;
    @BindView(R.id.comany_name)
    TextView comanyName;
    @BindView(R.id.comany_type)
    TextView comanyType;
    @BindView(R.id.company_credit)
    TextView companyCredit;
    @BindView(R.id.company_register)
    TextView companyRegister;
    @BindView(R.id.company_address)
    TextView companyAddress;
    @BindView(R.id.company_tel)
    TextView companyTel;
    @BindView(R.id.company_email)
    TextView companyEmail;
    @BindView(R.id.info_come)
    TextView infoCome;
    @BindView(R.id.company_laurel)
    RecyclerView companyLaurel;
    Unbinder unbinder;
    @BindView(R.id.legal_name)
    TextView legalName;
    @BindView(R.id.registe_money)
    TextView registeMoney;
    List<String>  mList = new ArrayList<>();
    private LaurelAdapter laurelAdapter;

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
        String eid = PrefUtils.getString(getContext(), "eid", "");
        companyLaurel.setLayoutManager(new GridLayoutManager(getContext(),2));
        laurelAdapter = new LaurelAdapter(R.layout.laurel_item, mList);
        companyLaurel.setAdapter(laurelAdapter);
        mPresenter.getDetailData("1");
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
    public void setBriefData(BriefBean briefBean) {
        System.out.println("introduce"+briefBean.getIntroduction());
        comIntroduce.setText("  " + briefBean.getIntroduction());
        comanyName.setText(briefBean.getName());
        legalName.setText(briefBean.getLegal_representative());
        System.out.println("monet"+briefBean.getRegistered_capital());
        registeMoney.setText(briefBean.getRegistered_capital());
        comanyType.setText(briefBean.getI_name());
    companyCredit.setText(briefBean.getCreditcode());
    companyRegister.setText(briefBean.getFounded_date());
    companyAddress.setText(briefBean.getAddr());
    companyTel.setText(briefBean.getPhone());
    companyEmail.setText(briefBean.getMailbox());
    infoCome.setText(briefBean.getSource());

    laurelAdapter.setNewData(briefBean.getHonor_img());



    }


}
