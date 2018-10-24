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
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerVideoComponent;
import zhonghuass.ssml.di.module.VideoModule;
import zhonghuass.ssml.mvp.contract.VideoContract;
import zhonghuass.ssml.mvp.model.appbean.PhotoBean;
import zhonghuass.ssml.mvp.presenter.VideoPresenter;
import zhonghuass.ssml.mvp.ui.adapter.PhotoAdapter;
import zhonghuass.ssml.mvp.ui.adapter.SlideInBottomAdapter;
import zhonghuass.ssml.mvp.ui.adapter.VedioAdapter;
import zhonghuass.ssml.utils.Constants;
import zhonghuass.ssml.utils.PrefUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class VideoFragment extends BaseFragment<VideoPresenter> implements VideoContract.View {

    @BindView(R.id.video_recy)
    RecyclerView videoRecy;
    @BindView(R.id.video_refresh)
    SwipeRefreshLayout videoRefresh;
    Unbinder unbinder;
    private List<PhotoBean> mList = new ArrayList<>();
    private int page=1;
    private String member_id="";
    private String member_type = "";
    private VedioAdapter photoAdapter;
    private boolean state = false;
    private String eid;
    private String target_type = "0";
    private String content_type = "1";
    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerVideoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .videoModule(new VideoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        page=1;
        eid = PrefUtils.getString(getActivity(), "eid", "");
        member_id = PrefUtils.getString(getActivity(), Constants.USER_ID, "");
        member_type = PrefUtils.getString(getActivity(), Constants.MEMBER_TYPE, "0");
        System.out.println("photo"+eid);
        initRecycle();
        mPresenter.getPhotoData(eid,target_type,content_type,member_id,member_type,page);
    }
    private void initRecycle() {
        photoAdapter = new VedioAdapter(getActivity(), mList);
        SlideInBottomAdapter slideInBottomAdapter = new SlideInBottomAdapter(photoAdapter);
        RecyclerViewHelper.initRecyclerViewSV(getActivity(),videoRecy,slideInBottomAdapter,2);
        videoRecy.setAdapter(photoAdapter);

        videoRefresh.setOnRefreshListener(()->{
            photoAdapter.enableLoadMore(false);
            page = 1;
            mPresenter.getPhotoData(eid,target_type,content_type,member_id,member_type,page);

        });
        photoAdapter.setRequestDataListener(()->{
            page++;
            mPresenter.getPhotoData(eid,target_type,content_type,member_id,member_type,page);
        });
        videoRecy.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
    public void notifystate() {

        photoAdapter.noMoreDataToast();
        if(state){

            Toast.makeText(getActivity(),"没有更多数据,请稍后尝试!",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void setContent(List<PhotoBean> data) {
        System.out.println("============="+data.size());
        if(videoRefresh.isRefreshing()){
            videoRefresh.setRefreshing(false);
        }
        photoAdapter.enableLoadMore(true);
        photoAdapter.loadComplete();
        if(page>1){
            photoAdapter.addItems(data);
        }else {
            photoAdapter.updateItems(data);
        }
        // photoAdapter.disableLoadMoreIfNotFullPage(photoRefresh);
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

}
