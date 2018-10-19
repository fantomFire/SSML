package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRequestDataListener;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerSearchResultComponent;
import zhonghuass.ssml.di.module.SearchResultModule;
import zhonghuass.ssml.mvp.contract.SearchResultContract;
import zhonghuass.ssml.mvp.model.appbean.RecommendBean;
import zhonghuass.ssml.mvp.presenter.SearchResultPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.mvp.ui.adapter.SlideInBottomAdapter;
import zhonghuass.ssml.mvp.ui.adapter.StaggeredGridAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class SearchResultActivity extends BaseActivity<SearchResultPresenter> implements SearchResultContract.View {

    @BindView(R.id.ll_search_back)
    LinearLayout llSearchBack;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.tv_search_right)
    TextView tvSearchRight;
    @BindView(R.id.rl_search_right)
    RelativeLayout rlSearchRight;
    @BindView(R.id.rv_search_result)
    RecyclerView rvSearchResult;
    @BindView(R.id.srl_search_result)
    SwipeRefreshLayout srlSearchResult;
    private StaggeredGridAdapter mAdapter;
    private int page = 1;
    private String member_id="1",member_type="0";
    private List<RecommendBean> mList;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSearchResultComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .searchResultModule(new SearchResultModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_search_result; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecycleView();
    }

    private void initRecycleView() {
        mAdapter = new StaggeredGridAdapter(this, mList);
        SlideInBottomAdapter slideAdapter = new SlideInBottomAdapter(mAdapter);
        RecyclerViewHelper.initRecyclerViewSV(this, rvSearchResult, slideAdapter, 2);
        mAdapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                page++;
                mPresenter.getSearchResult(member_id, member_type, page);
            }
        });
        srlSearchResult.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mAdapter.enableLoadMore(false);
                page = 1;
                mPresenter.getSearchResult(member_id, member_type, page);
            }
        });
    /*    mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                System.out.println("当前位置");
                if(null!=mData){
                    System.out.println("当前位置"+position);
                    Intent intent = new Intent(getActivity(), GraphicDetailsActivity.class);
                    intent.putExtra("content_id",mData.get(position).getContent_id());
                    intent.putExtra("member_id",mData.get(position).getMember_id());
                    intent.putExtra("member_type",mData.get(position).getMember_type());
                    startActivity(intent);
                }

            }
        });*/
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
        finish();
    }


    @Override
    public void setContent(List<RecommendBean> data) {
        System.out.println(data.get(0).getContent_title());
    }

    @Override
    public void notifystate() {

    }
}
