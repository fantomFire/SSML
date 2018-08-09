package zhonghuass.ssml.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.contract.MycenterContract;
import zhonghuass.ssml.mvp.model.appbean.RecommendBean;
import zhonghuass.ssml.mvp.presenter.MycenterPresenter;
import zhonghuass.ssml.mvp.ui.adapter.RecommendAdapter;
import zhonghuass.ssml.utils.decoration.SpacesItemDecoration;


public class MyPicTextFragment extends BaseFragment<MycenterPresenter> implements IFragment, MycenterContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private List<RecommendBean> recommendBeans;
    private RecommendAdapter recommendAdapter;


    public static MyPicTextFragment newInstance() {
        MyPicTextFragment fragment = new MyPicTextFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_pic, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        recommendBeans = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            RecommendBean recommendBean = new RecommendBean();
            if (i % 2 == 0) {
                recommendBean .imgPath = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532681290688&di=7bed793d89319d18418830c0e68a5cb8&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F4b90f603738da977f33b82f9bc51f8198718e3c8.jpg";
                recommendBean.companyName = "美女与野兽" + i;
            } else {
                recommendBean.imgPath = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532681353310&di=3a05928430c3412edcec58df868d74cb&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F2e2eb9389b504fc213b73cb0e3dde71191ef6d9f.jpg";
                recommendBean.companyName = "V字仇杀队" + i;
            }
            recommendBeans.add(recommendBean);
        }

//        recommendAdapter = new RecommendAdapter(R.layout.recommend_item, recommendBeans);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);//定义瀑布流管理器，第一个参数是列数，第二个是方向。
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);//不设置的话，图片闪烁错位，有可能有整列错位的情况。
        mRecyclerView.setLayoutManager(layoutManager);//设置瀑布流管理器
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(0, 0));//边距和分割线，需要自己定义
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                layoutManager.invalidateSpanAssignments();//这行主要解决了当加载更多数据时，底部需要重绘，否则布局可能衔接不上。
            }
        });
//        mRecyclerView.setAdapter(recommendAdapter);

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

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }
}
