package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerSearchComponent;
import zhonghuass.ssml.di.module.SearchModule;
import zhonghuass.ssml.mvp.contract.SearchContract;
import zhonghuass.ssml.mvp.model.appbean.HistoryBean;
import zhonghuass.ssml.mvp.presenter.SearchPresenter;
import zhonghuass.ssml.mvp.ui.adapter.SearchHistoryAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.View {

    @BindView(R.id.ll_search_back)
    LinearLayout llSearchBack;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.tv_search_right)
    TextView tvSearchRight;
    @BindView(R.id.rv_hot_search)
    RecyclerView rvHotSearch;
    @BindView(R.id.rv_history_delete)
    RelativeLayout rvHistoryDelete;
    @BindView(R.id.rv_search_history)
    RecyclerView rvSearchHistory;
    @BindView(R.id.rl_search_right)
    RelativeLayout rlSearchRight;
    private SearchHistoryAdapter historyAdapter;
    private String member_id = "1", member_type = "1";
    private List<HistoryBean> mList = new ArrayList<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSearchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .searchModule(new SearchModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_search; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        rvSearchHistory.setLayoutManager(gridLayoutManager);
        historyAdapter = new SearchHistoryAdapter(R.layout.search_history_item, mList);
        rvSearchHistory.setAdapter(historyAdapter);
        mPresenter.getSearchHistoryData(member_id, member_type);


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
    public void setSearchHistory(List<HistoryBean> data) {
        mList.clear();
        mList.addAll(data);
        historyAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.ll_search_back, R.id.edt_search, R.id.rv_history_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_search_back:
                break;
            case R.id.edt_search:
                ArmsUtils.startActivity(SearchResultActivity.class);
                break;
            case R.id.rv_history_delete:
                break;
        }
    }
}
