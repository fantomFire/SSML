package zhonghuass.ssml.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.PopupWindow;

import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.pickerView.scrollPicker.CustomCityPicker;
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
import zhonghuass.ssml.mvp.model.appbean.TradeItemBean;
import zhonghuass.ssml.mvp.presenter.CompanyPresenter;
import zhonghuass.ssml.mvp.ui.adapter.TradeAdapter;
import zhonghuass.ssml.mvp.ui.adapter.TradeItemAdapter;

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
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    Unbinder unbinder1;
    private String area;
    private String type;
    private int currentPage = 1;
    private int pagesize = 5;
    private List<TradeBean> mList = new ArrayList<>();
    private List<TradeItemBean> tradeData = new ArrayList<>();
    private TradeAdapter tradeAdapter;
    private CustomCityPicker cityPicker;
    private PopupWindow popupWindow;
    private TradeItemAdapter tradeItemAdapter;

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
        initPopupWindow();
        //获取默认信息
        mPresenter.getTradeData(area, type, currentPage, pagesize);
        //获取区域
        mPresenter.getAreaData();
        //获取行业
        mPresenter.getTradeItem();

        cityPicker = new CustomCityPicker(getContext(), new CustomCityPicker.ResultHandler() {
            @Override
            public void handle(String result) {
                // tvArea.setText(result);
                System.out.println("area" + result);
            }

            @Override
            public void sendId(String ids) {
                int i = ids.lastIndexOf("-");
                area = ids.substring(i);
                mPresenter.getTradeData(area, type, currentPage, pagesize);
            }
        });
        //提前初始化数据，这样可以加载快一些。
    }

    private void initPopupWindow() {
        popupWindow = new PopupWindow(getContext());
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View popView = LayoutInflater.from(getContext()).inflate(R.layout.layout_popupwindow, null);
        RecyclerView mRecycle = popView.findViewById(R.id.trade_rec);
        mRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        tradeItemAdapter = new TradeItemAdapter(R.layout.trade_items, tradeData);
        mRecycle.setAdapter(tradeItemAdapter);

        popupWindow.setContentView(popView);

        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));

        popupWindow.setOutsideTouchable(false);

        popupWindow.setFocusable(true);

        tradeItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                System.out.println("item" + position);
                type = tradeData.get(position).getI_id();
                mPresenter.getTradeData(area, type, currentPage, pagesize);
                popupWindow.dismiss();
            }
        });
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
                cityPicker.show();
                break;
            case R.id.ll_trade:
                showPopupWindow();
                break;
        }
    }


    @Override
    public void showTradeData(List<TradeBean> data) {

        mList.clear();
        mList.addAll(data);
        tradeAdapter.notifyDataSetChanged();

    }

    @Override
    public void showAreaData(String datas) {
        if (null != datas) {
            cityPicker.initJson(datas);
        }
    }

    @Override
    public void showTradeItem(List<TradeItemBean> data) {
        tradeData = data;
    }

    private void showPopupWindow() {
        popupWindow.showAsDropDown(llTitle,0,10);
        tradeItemAdapter.setNewData(tradeData);


    }

}
