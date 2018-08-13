package zhonghuass.ssml.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.library.baseAdapter.BaseQuickAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerRecommendComponent;
import zhonghuass.ssml.di.module.RecommendModule;
import zhonghuass.ssml.mvp.contract.RecommendContract;
import zhonghuass.ssml.mvp.model.appbean.RecommendBean;
import zhonghuass.ssml.mvp.presenter.RecommendPresenter;
import zhonghuass.ssml.mvp.ui.adapter.RecommendAdapter;
import zhonghuass.ssml.utils.decoration.SpacesItemDecoration;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class RecommendFragment extends BaseFragment<RecommendPresenter> implements RecommendContract.View {

    @BindView(R.id.recommend_rec)
    RecyclerView recommendRec;
    private ArrayList<RecommendBean> recommendDatas = new ArrayList<>();
    private RecommendAdapter recommendAdapter;

    private String member_id = "1";
    private String member_type = "1";
    private int page = 1;
    private boolean isloadMore = false;

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
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //RecyclerView滑动过程中不断请求layout的Request，不断调整item见的间隙，并且是在item尺寸显示前预处理，因此解决RecyclerView滑动到顶部时仍会出现移动问题
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//防止item 交换位置
        recommendRec.setLayoutManager(layoutManager);
//        recommendRec.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        recommendRec.setPadding(0, 0, 0, 0);
        recommendRec.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                layoutManager.invalidateSpanAssignments();//防止第一行到顶部有空白区域
            }
        });
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recommendRec.setLayoutManager(layoutManager);
        recommendAdapter = new RecommendAdapter(R.layout.recommend_item, recommendDatas);
        recommendRec.setAdapter(recommendAdapter);
        // recommendRec.addItemDecoration(new SpacesItemDecoration(10,10,getResources().getColor(R.color.colorf5)));
        recommendAdapter.setOnLoadMoreListener(() -> {
                    isloadMore = true;
                    page++;
                    mPresenter.getRecomendData(member_id, member_type, page);
                }
        );
    }

    @Override
    public void onResume() {
        super.onResume();

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
    public void setContent(List<RecommendBean> data) {
        System.out.println("page" + page);
//        recommendAdapter.loadMoreComplete();

        recommendAdapter.loadMoreComplete();

        if (isloadMore) {
            recommendAdapter.addData(data);
//            recommendDatas.addAll(data);
//            recommendAdapter.notifyDataSetChanged();
//            recommendAdapter.loadMoreComplete();
        } else {
            recommendAdapter.setNewData(data);
//            recommendDatas.clear();
//            recommendDatas.addAll(data);
//            recommendAdapter.notifyDataSetChanged();
//            recommendAdapter.loadMoreComplete();
        }


    }

    @Override
    public void notifystate() {
        recommendAdapter.loadMoreEnd();
        isloadMore = false;
    }
}
