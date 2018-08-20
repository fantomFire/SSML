package zhonghuass.ssml.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.library.baseAdapter.BaseQuickAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerCompanyRecommendComponent;
import zhonghuass.ssml.di.module.CompanyRecommendModule;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.CompanyRecommendContract;
import zhonghuass.ssml.mvp.model.appbean.ComanyrfBean;
import zhonghuass.ssml.mvp.presenter.CompanyRecommendPresenter;
import zhonghuass.ssml.mvp.ui.adapter.ComreAdapter;
import zhonghuass.ssml.utils.Constants;
import zhonghuass.ssml.utils.PrefUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class CompanyRecommendFragment extends BaseFragment<CompanyRecommendPresenter> implements CompanyRecommendContract.View, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.comany_recycle)
    RecyclerView comanyRecycle;
    Unbinder unbinder;
    @BindView(R.id.tv_comment_count)
    TextView tvCommentCount;
    private int page = 1;
    private int pagesize = 5;
    private ComreAdapter adapter;
    private List<ComanyrfBean.ListBean> list;
    private String ep_id;

    public static CompanyRecommendFragment newInstance() {
        CompanyRecommendFragment fragment = new CompanyRecommendFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCompanyRecommendComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .companyRecommendModule(new CompanyRecommendModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_company_recommend, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecycleView();
        ep_id = PrefUtils.getString(getContext(), Constants.EP_ID, "");
        //网络请求数据-产品
        mPresenter.getcomanyrfData("1", page,pagesize);
    }


    private void initRecycleView() {
        comanyRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ComreAdapter(R.layout.compony_recommend, list);
        //上拉加载
        adapter.setOnLoadMoreListener(this);

        comanyRecycle.setAdapter(adapter);
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
        ArmsUtils.makeText(getContext(), message);
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
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void showComanyData(BaseResponse<ComanyrfBean> listBaseResponse) {
        list = listBaseResponse.getData().list;
        tvCommentCount.setText("共" + listBaseResponse.getData().count + "产品");

        adapter.setEnableLoadMore(true);
        if (list.size() > 0) {
            adapter.loadMoreComplete();
        } else {
            adapter.loadMoreEnd();
            return;
        }
        if (page == 1) {
            adapter.setNewData(list);
        } else {
            adapter.addData(list);
        }
        adapter.disableLoadMoreIfNotFullPage(comanyRecycle);
    }

    @Override
    public void addshowData() {
        adapter.notifyLoadMoreToLoading();
        ArmsUtils.makeText(getContext(),"没有更多数据,请稍后尝试!");
    }


    @Override
    public void onLoadMoreRequested() {
        page++;
        mPresenter.getcomanyrfData(ep_id, page, page);
    }

}
