package zhonghuass.ssml.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRequestDataListener;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerRecommendComponent;
import zhonghuass.ssml.di.module.RecommendModule;
import zhonghuass.ssml.mvp.EventMsg;
import zhonghuass.ssml.mvp.contract.RecommendContract;
import zhonghuass.ssml.mvp.model.appbean.RecommendBean;
import zhonghuass.ssml.mvp.presenter.RecommendPresenter;
import zhonghuass.ssml.mvp.ui.adapter.RecommendAdapter;
import zhonghuass.ssml.mvp.ui.adapter.SlideInBottomAdapter;
import zhonghuass.ssml.mvp.ui.adapter.StaggeredGridAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class RecommendFragment extends BaseFragment<RecommendPresenter> implements RecommendContract.View {

    @BindView(R.id.recommend_rec)
    RecyclerView recommendRec;
    @BindView(R.id.recommend_srl)
    SwipeRefreshLayout recommendSrl;
    private List<RecommendBean> recommendDatas = new ArrayList<>();

    private String member_id = "1";
    private String member_type = "1";
    private int page = 1;
    private StaggeredGridAdapter mAdapter;

    public static RecommendFragment newInstance() {
        RecommendFragment fragment = new RecommendFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerRecommendComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .recommendModule(new RecommendModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recommend, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecycleView();
        mPresenter.getRecomendData(member_id, member_type, page);

    }

    private void initRecycleView() {
        mAdapter = new StaggeredGridAdapter(getContext(), recommendDatas);
        SlideInBottomAdapter slideAdapter = new SlideInBottomAdapter(mAdapter);
        RecyclerViewHelper.initRecyclerViewSV(getContext(), recommendRec, slideAdapter, 2);
        mAdapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                page++;
                mPresenter.getRecomendData(member_id, member_type, page);
            }
        });
        recommendSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mAdapter.enableLoadMore(false);
                page = 1;
                mPresenter.getRecomendData(member_id, member_type, page);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void setData(@Nullable Object data) {
        System.out.println((int)data+"ppppppppp");

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
    public void setContent(List<RecommendBean> data) {
        if (recommendSrl.isRefreshing()) {
            recommendSrl.setRefreshing(false);
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
        mAdapter.disableLoadMoreIfNotFullPage(recommendRec);
    }
}
