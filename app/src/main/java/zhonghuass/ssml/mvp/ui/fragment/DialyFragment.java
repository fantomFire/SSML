package zhonghuass.ssml.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRequestDataListener;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerDialyComponent;
import zhonghuass.ssml.di.module.DialyModule;
import zhonghuass.ssml.mvp.contract.DialyContract;
import zhonghuass.ssml.mvp.model.appbean.DailyBean;
import zhonghuass.ssml.mvp.model.appbean.DailyChoicenessBean;
import zhonghuass.ssml.mvp.presenter.DialyPresenter;
import zhonghuass.ssml.mvp.ui.adapter.DailyAdapter;
import zhonghuass.ssml.mvp.ui.adapter.SlideInBottomAdapter;
import zhonghuass.ssml.utils.CircleImageView;
import zhonghuass.ssml.utils.Constants;
import zhonghuass.ssml.utils.PrefUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class DialyFragment extends BaseFragment<DialyPresenter> implements DialyContract.View {

    @BindView(R.id.rv_daily)
    RecyclerView rvDaily;
    @BindView(R.id.srl_daily)
    SwipeRefreshLayout srlDaily;
    private DailyAdapter mAdapter;
    private List<DailyBean.DataBean.RankingListBean> mData = new ArrayList<>();
    private List<DailyChoicenessBean> mList = new ArrayList<>();
    private TextView tvLabel, tvFirstName, tvFirstIntro, tvSecondName, tvSecondIntro, tvThirdlyIntro, tvThirdlyName;
    private CircleImageView civFirstIcon, civSecondIcon, civThirdlyIcon;
    private String member_id , member_type ;
    private int page = 1;


    public static DialyFragment newInstance() {
        DialyFragment fragment = new DialyFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerDialyComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .dialyModule(new DialyModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialy, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        member_id = PrefUtils.getString(getActivity(), Constants.USER_ID, "");
        member_type = PrefUtils.getString(getActivity(), Constants.MEMBER_TYPE, "0");
        initRecycleView();

    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            //请求每日一语排行数据
            mPresenter.getDailyHeaderData();
            //请求每日一语精选数据
            mPresenter.getDailyData(member_id, member_type, page);
        }

    }
    /**
     * 初始化RecycleView及Adapter
     * 为RecycleView添加头布局
     */
    private void initRecycleView() {
        mAdapter = new DailyAdapter(getContext(), mList);
        View headerView = getLayoutInflater().inflate(R.layout.daily_header, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tvLabel = headerView.findViewById(R.id.tv_label);
        civFirstIcon = headerView.findViewById(R.id.civ_first_icon);
        civSecondIcon = headerView.findViewById(R.id.civ_second_icon);
        civThirdlyIcon = headerView.findViewById(R.id.civ_thirdly_icon);
        tvFirstName = headerView.findViewById(R.id.tv_first_name);
        tvFirstIntro = headerView.findViewById(R.id.tv_first_intro);
        tvSecondName = headerView.findViewById(R.id.tv_second_name);
        tvSecondIntro = headerView.findViewById(R.id.tv_second_intro);
        tvThirdlyIntro = headerView.findViewById(R.id.tv_thirdly_intro);
        tvThirdlyName = headerView.findViewById(R.id.tv_thirdly_name);
        mAdapter.addHeaderView(headerView);
        SlideInBottomAdapter slideAdapter = new SlideInBottomAdapter(mAdapter);
        RecyclerViewHelper.initRecyclerViewSV(getContext(), rvDaily, slideAdapter, 2);
        mAdapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                page++;
                mPresenter.getDailyData(member_id, member_type, page);
            }
        });
        srlDaily.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.enableLoadMore(false);
                page = 1;
                mPresenter.getDailyHeaderData();
                mPresenter.getDailyData(member_id, member_type, page);
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


    @Override
    public void showDailyHeaderData(DailyBean data) {
        tvLabel.setText(data.getData().getTheme_title());
        mData = data.getData().getRanking_list();
        if (mData.size() > 0) {
            Glide.with(getContext())
                    .load(mData.get(0).getMember_image())
                    .into(civSecondIcon);
            tvSecondName.setText(mData.get(0).getMember_name());
            tvSecondIntro.setText(mData.get(0).getContent_title());
        }
        if (mData.size() > 1) {
            Glide.with(getContext())
                    .load(mData.get(1).getMember_image())
                    .into(civFirstIcon);
            tvFirstName.setText(mData.get(1).getMember_name());
            tvFirstIntro.setText(mData.get(1).getContent_title());
        }
        if (mData.size() > 2) {
            Glide.with(getContext())
                    .load(mData.get(2).getMember_image())
                    .into(civThirdlyIcon);
            tvThirdlyName.setText(mData.get(2).getMember_name());
            tvThirdlyIntro.setText(mData.get(2).getContent_title());
        }
    }

    @Override
    public void showDailyData(List<DailyChoicenessBean> data) {
        if (srlDaily.isRefreshing()) {
            srlDaily.setRefreshing(false);
        }
        mAdapter.enableLoadMore(true);
        mAdapter.loadComplete();
        if (page > 1) {
            mAdapter.addItems(data);
        } else {
            mAdapter.updateItems(data);
        }
    }

    @Override
    public void notifystate() {
        mAdapter.noMoreDataToast();
    }
}
