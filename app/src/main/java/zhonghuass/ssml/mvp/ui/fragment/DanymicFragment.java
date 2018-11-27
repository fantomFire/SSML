package zhonghuass.ssml.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRequestDataListener;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerDanymicComponent;
import zhonghuass.ssml.di.module.DanymicModule;
import zhonghuass.ssml.mvp.contract.DanymicContract;
import zhonghuass.ssml.mvp.model.appbean.DanynimicBean;
import zhonghuass.ssml.mvp.presenter.DanymicPresenter;
import zhonghuass.ssml.mvp.ui.adapter.DanymicAdapter;
import zhonghuass.ssml.mvp.ui.adapter.SlideInBottomAdapter;
import zhonghuass.ssml.utils.Constants;
import zhonghuass.ssml.utils.PrefUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class DanymicFragment extends BaseFragment<DanymicPresenter> implements DanymicContract.View {

    private boolean mViewCreated = false;
    @BindView(R.id.recommend_dny)
    RecyclerView recommendDny;
    @BindView(R.id.dany_refresh)
    SwipeRefreshLayout danyRefresh;
    private String member_id ;
    private String member_type;
    private int page = 1;
    private DanymicAdapter danymicAdapter;
    List<DanynimicBean> mlist = new ArrayList<>();

    public static DanymicFragment newInstance() {
        DanymicFragment fragment = new DanymicFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerDanymicComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .danymicModule(new DanymicModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_danymic, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        member_id = PrefUtils.getString(getActivity(), Constants.USER_ID, "");
        member_type = PrefUtils.getString(getActivity(), Constants.MEMBER_TYPE, "0");

        initRecycleView();
        mViewCreated = true;


    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser&&mViewCreated){
            mPresenter.getDanymicData(member_id, member_type, page);
        }
    }

    private void initRecycleView() {
        danymicAdapter = new DanymicAdapter(getActivity(), mlist);
        SlideInBottomAdapter slideAdapter = new SlideInBottomAdapter(danymicAdapter);
        RecyclerViewHelper.initRecyclerViewSV(getContext(), recommendDny, slideAdapter, 2);
        recommendDny.setAdapter(danymicAdapter);

        danymicAdapter.setRequestDataListener(() -> {
            if(!danyRefresh.isRefreshing()){

                page++;
                mPresenter.getDanymicData(member_id, member_type, page);
            }
        });
        danyRefresh.setOnRefreshListener(() -> {
            page = 1;
            danymicAdapter.enableLoadMore(false);
            mPresenter.getDanymicData(member_id, member_type, page);

        });
        recommendDny.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                System.out.println("=====" + dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                System.out.println("state" + newState);
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
    public void setContent(List<DanynimicBean> listDamnymic) {
        if (danyRefresh.isRefreshing()) {
            danyRefresh.setRefreshing(false);
        }
        danymicAdapter.enableLoadMore(true);
        danymicAdapter.loadComplete();
        if (page > 1) {
            danymicAdapter.addItems(listDamnymic);
        } else {
            danymicAdapter.updateItems(listDamnymic);
        }


    }

    @Override
    public void notifystate() {
        danymicAdapter.noMoreData();
       // Toast.makeText(getActivity(), "没有更多数据,请稍后尝试!", Toast.LENGTH_SHORT).show();
    }
}
