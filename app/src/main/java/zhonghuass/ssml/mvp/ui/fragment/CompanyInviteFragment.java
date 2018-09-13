package zhonghuass.ssml.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import butterknife.ButterKnife;
import butterknife.Unbinder;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerCompanyInviteComponent;
import zhonghuass.ssml.di.module.CompanyInviteModule;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.CompanyInviteContract;
import zhonghuass.ssml.mvp.model.appbean.IniviteBean;
import zhonghuass.ssml.mvp.presenter.CompanyInvitePresenter;
import zhonghuass.ssml.mvp.ui.adapter.IniviteAdapter;
import zhonghuass.ssml.utils.Constants;
import zhonghuass.ssml.utils.PrefUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class CompanyInviteFragment extends BaseFragment<CompanyInvitePresenter> implements CompanyInviteContract.View, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.tv_invite_count)
    TextView tvInviteCount;
    @BindView(R.id.invite_recycle)
    RecyclerView inviteRecycle;
    Unbinder unbinder;
    private int page = 1;
    private int pagesize = 5;
    private String ep_id;
    private List<IniviteBean.ListBean> list;
    private IniviteAdapter iniviteAdapter;

    public static CompanyInviteFragment newInstance() {
        CompanyInviteFragment fragment = new CompanyInviteFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCompanyInviteComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .companyInviteModule(new CompanyInviteModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_company_invite, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ep_id = PrefUtils.getString(getContext(), Constants.EP_ID, "");

        inviteRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        iniviteAdapter = new IniviteAdapter(R.layout.compony_invite, list);
        iniviteAdapter.openLoadAnimation();
        iniviteAdapter.setOnLoadMoreListener(this);
        inviteRecycle.setAdapter(iniviteAdapter);
        mPresenter.getInviteData("1", page, pagesize);
    }

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
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
    public void showdata(BaseResponse<IniviteBean> iniviteBeanBaseResponse) {
        int count = iniviteBeanBaseResponse.getData().count;
        list = iniviteBeanBaseResponse.getData().list;
        tvInviteCount.setText("共" + count + "职位");

        if (list.size() > 0) {
            iniviteAdapter.loadMoreComplete();
        } else {
            iniviteAdapter.loadMoreEnd();
            return;
        }
        if (page == 1) {
            iniviteAdapter.setNewData(list);
        } else {
            iniviteAdapter.addData(list);
        }
        iniviteAdapter.disableLoadMoreIfNotFullPage(inviteRecycle);
    }

    @Override
    public void showdatatoast() {

    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        mPresenter.getInviteData("1", page, pagesize);
    }
}
