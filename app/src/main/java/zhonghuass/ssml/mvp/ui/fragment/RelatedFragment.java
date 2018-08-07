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

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.OnClick;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerRelatedComponent;
import zhonghuass.ssml.di.module.RelatedModule;
import zhonghuass.ssml.mvp.contract.RelatedContract;
import zhonghuass.ssml.mvp.presenter.RelatedPresenter;
import zhonghuass.ssml.mvp.ui.activity.CommentActivity;
import zhonghuass.ssml.mvp.ui.activity.ConcernActivity;
import zhonghuass.ssml.mvp.ui.activity.MessageListActivity;
import zhonghuass.ssml.mvp.ui.activity.PraiseActivity;
import zhonghuass.ssml.mvp.ui.activity.ShareMeActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 与我相关页面
 */
public class RelatedFragment extends BaseFragment<RelatedPresenter> implements RelatedContract.View {

    @BindView(R.id.ll_concern)
    LinearLayout llConcern;
    @BindView(R.id.ll_praise)
    LinearLayout llPraise;
    @BindView(R.id.ll_comment)
    LinearLayout llComment;
    @BindView(R.id.ll_share)
    LinearLayout llShare;
    @BindView(R.id.ll_message)
    LinearLayout llMessage;


    public static RelatedFragment newInstance() {
        RelatedFragment fragment = new RelatedFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerRelatedComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .relatedModule(new RelatedModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_related, container, false);
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


    @OnClick({R.id.ll_concern, R.id.ll_praise, R.id.ll_comment, R.id.ll_share, R.id.ll_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_concern:
                //跳转到消息--与我相关--关注我的页面
                ArmsUtils.startActivity(ConcernActivity.class);
                break;
            case R.id.ll_praise:
                //跳转到消息--与我相关--赞我的页面
                ArmsUtils.startActivity(PraiseActivity.class);
                break;
            case R.id.ll_comment:
                //跳转到消息--与我相关--评论我的页面
                ArmsUtils.startActivity(CommentActivity.class);
                break;
            case R.id.ll_share:
                //跳转到消息--与我相关--分享我的页面
                ArmsUtils.startActivity(ShareMeActivity.class);
                break;
            case R.id.ll_message:
                //跳转到消息--与我相关--私信我的页面
                ArmsUtils.startActivity(MessageListActivity.class);
                break;
        }
    }
}
