package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerSearchComponent;
import zhonghuass.ssml.di.module.SearchModule;
import zhonghuass.ssml.mvp.contract.SearchContract;
import zhonghuass.ssml.mvp.presenter.SearchPresenter;

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
}
