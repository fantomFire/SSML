package zhonghuass.ssml.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.github.library.baseAdapter.animation.SlideInBottomAnimation;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerFocusComponent;
import zhonghuass.ssml.di.module.FocusModule;
import zhonghuass.ssml.mvp.contract.FocusContract;
import zhonghuass.ssml.mvp.model.appbean.FocusBean;
import zhonghuass.ssml.mvp.presenter.FocusPresenter;
import zhonghuass.ssml.mvp.ui.adapter.FocusAdapter;
import zhonghuass.ssml.mvp.ui.adapter.SlideInBottomAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class FocusFragment extends BaseFragment<FocusPresenter> implements FocusContract.View {
    private boolean state = false;
    @BindView(R.id.fo_recy)
    RecyclerView foRecy;
    @BindView(R.id.refresh_fo)
    SwipeRefreshLayout refreshFo;
    Unbinder unbinder;
    private List<FocusBean> mList = new ArrayList<>();
    private FocusAdapter focusAdapter;
    private int page;
    private String member_id="1";
    private String member_type = "1";

    public static FocusFragment newInstance() {
        FocusFragment fragment = new FocusFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerFocusComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .focusModule(new FocusModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_focus, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecycleView();
        mPresenter.getFocusData(member_id,member_type,page);



    }

    private void initRecycleView() {
        focusAdapter = new FocusAdapter(getActivity(), mList);
        SlideInBottomAdapter slideInBottomAdapter = new SlideInBottomAdapter(focusAdapter);
        RecyclerViewHelper.initRecyclerViewSV(getActivity(),foRecy,slideInBottomAdapter,2);
        foRecy.setAdapter(focusAdapter);
        refreshFo.setOnRefreshListener(()->{
            focusAdapter.enableLoadMore(false);
            page = 1;
            mPresenter.getFocusData(member_id,member_type,page);

        });
        focusAdapter.setRequestDataListener(()->{
            page++;
            mPresenter.getFocusData(member_id,member_type,page);

        });
        foRecy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0){

                    state = true;
                }
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
    public void notifystate() {

        focusAdapter.noMoreDataToast();
        if(state){

            Toast.makeText(getActivity(),"没有更多数据,请稍后尝试!",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void setContent(List<FocusBean> data) {
        if(refreshFo.isRefreshing()){
            refreshFo.setRefreshing(false);
        }
        focusAdapter.enableLoadMore(true);
        focusAdapter.loadComplete();
        if(page>1){
            focusAdapter.addItems(data);
        }else {
            focusAdapter.updateItems(data);
        }
       // focusAdapter.disableLoadMoreIfNotFullPage(foRecy);
    }

}
