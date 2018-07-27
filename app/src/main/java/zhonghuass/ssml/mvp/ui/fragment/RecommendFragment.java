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

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;

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
    private ArrayList<RecommendBean> recommendBeans;
    private RecommendAdapter recommendAdapter;


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
        recommendBeans = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            RecommendBean recommendBean = new RecommendBean();
            recommendBean.imgPath = "http://pic1.cxtuku.com/00/10/37/b212a9aabf1b.jpg";
            recommendBean.companyName = "八马乐园" + i;
            recommendBeans.add(recommendBean);
        }
        RecommendBean recommendBean = new RecommendBean();
        recommendBean.imgPath = "http://pic2.cxtuku.com/00/03/24/b9931efe7466.jpg";
        recommendBean.companyName = "花好月圆";
        recommendBeans.add(recommendBean);


        initRecycleView();
    }

    private void initRecycleView() {
        recommendRec.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recommendAdapter = new RecommendAdapter(R.layout.recommend_item, recommendBeans);
        recommendRec.setAdapter(recommendAdapter);
        recommendRec.addItemDecoration(new SpacesItemDecoration(6,6,getResources().getColor(R.color.red)));
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("adsfadfadfa");
        recommendAdapter.addData(recommendBeans);
        recommendAdapter.notifyDataSetChanged();
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
