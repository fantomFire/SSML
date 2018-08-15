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
    private String member_id = "1", member_type = "1";
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
        initRecycleView();
        //请求每日一语排行数据
        mPresenter.getDailyHeaderData();
        //请求每日一语精选数据
        mPresenter.getDailyData(member_id, member_type, page);
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

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
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
        mAdapter.noMoreData();
    }
}
