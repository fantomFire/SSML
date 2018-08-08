package zhonghuass.ssml.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerSystemComponent;
import zhonghuass.ssml.di.module.SystemModule;
import zhonghuass.ssml.mvp.contract.SystemContract;
import zhonghuass.ssml.mvp.model.appbean.SysetemBean;
import zhonghuass.ssml.mvp.presenter.SystemPresenter;
import zhonghuass.ssml.mvp.ui.adapter.SysetemMessageAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 系统消息页面
 */

public class SystemFragment extends BaseFragment<SystemPresenter> implements SystemContract.View {

    @BindView(R.id.tv_communique_message)
    TextView tvCommuniqueMessage;
    @BindView(R.id.tv_communique_date)
    TextView tvCommuniqueDate;
    @BindView(R.id.ll_communique)
    LinearLayout llCommunique;
    @BindView(R.id.tv_editors_message)
    TextView tvEditorsMessage;
    @BindView(R.id.tv_editors_date)
    TextView tvEditorsDate;
    @BindView(R.id.ll_editors)
    LinearLayout llEditors;
    @BindView(R.id.tv_cute_hand_message)
    TextView tvCuteHandMessage;
    @BindView(R.id.tv_cute_hand_date)
    TextView tvCuteHandDate;
    @BindView(R.id.ll_cute_hand)
    LinearLayout llCuteHand;
    Unbinder unbinder;
    private SysetemMessageAdapter sysetemMessageAdapter;
    private List<SysetemBean> mList;

    public static SystemFragment newInstance() {
        SystemFragment fragment = new SystemFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSystemComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .systemModule(new SystemModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_system, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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
    public void showSystemData(List<SysetemBean> data) {

    }


    @OnClick({R.id.ll_communique, R.id.ll_editors, R.id.ll_cute_hand})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_communique:
                break;
            case R.id.ll_editors:
                break;
            case R.id.ll_cute_hand:
                break;
        }
    }
}
