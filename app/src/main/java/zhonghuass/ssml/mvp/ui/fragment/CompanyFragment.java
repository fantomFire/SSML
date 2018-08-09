package zhonghuass.ssml.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.library.baseAdapter.listener.OnItemClickListener;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerCompanyComponent;
import zhonghuass.ssml.di.module.CompanyModule;
import zhonghuass.ssml.mvp.contract.CompanyContract;
import zhonghuass.ssml.mvp.model.appbean.TradeBean;
import zhonghuass.ssml.mvp.presenter.CompanyPresenter;
import zhonghuass.ssml.mvp.ui.adapter.TradeAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class CompanyFragment extends BaseFragment<CompanyPresenter> implements CompanyContract.View {

    @BindView(R.id.icon_area)
    ImageView iconArea;
    @BindView(R.id.ll_area)
    LinearLayout llArea;
    @BindView(R.id.icon_trade)
    ImageView iconTrade;
    @BindView(R.id.ll_trade)
    LinearLayout llTrade;
    @BindView(R.id.trade_recycle)
    RecyclerView tradeRecycle;
    Unbinder unbinder;
    private String area;
    private String type;
    private int currentPage=1;
    private int pagesize=5;
    private List<TradeBean> mList = new ArrayList<>();
    private TradeAdapter tradeAdapter;

    public static CompanyFragment newInstance() {
        CompanyFragment fragment = new CompanyFragment();
        return fragment;
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCompanyComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .companyModule(new CompanyModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_company, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tradeRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        tradeAdapter = new TradeAdapter(R.layout.trade_item, mList);
        tradeRecycle.setAdapter(tradeAdapter);

        mPresenter.getTradeData(area, type, currentPage, pagesize);

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

    @OnClick({R.id.ll_area, R.id.ll_trade})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_area:
                break;
            case R.id.ll_trade:
                break;
        }
    }


    @Override
    public void showTradeData(List<TradeBean> data) {
        System.out.println("===="+data.get(0).getShortname());
       // tradeAdapter.addData(data);
        mList.clear();
        mList.addAll(data);
        tradeAdapter.notifyDataSetChanged();

    }
}
