package zhonghuass.ssml.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import zhonghuass.ssml.di.component.DaggerImageLayout1Component;
import zhonghuass.ssml.di.module.ImageLayout1Module;
import zhonghuass.ssml.mvp.ToActivityMsg;
import zhonghuass.ssml.mvp.ToFragmentMsg;
import zhonghuass.ssml.mvp.contract.ImageLayout1Contract;
import zhonghuass.ssml.mvp.presenter.ImageLayout1Presenter;

import zhonghuass.ssml.R;
import zhonghuass.ssml.utils.EventBusUtils;

import java.util.ArrayList;
import java.util.List;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ImageLayout1Fragment extends BaseFragment<ImageLayout1Presenter> implements ImageLayout1Contract.View {

    @BindView(R.id.rl_bg)
    public RelativeLayout rlBg;
    @BindView(R.id.rl_wl)
    public RelativeLayout rlWl;
    @BindView(R.id.tv1)
    public TextView tv1;
    private ToActivityMsg eventMsg;
    private boolean isclick;

    public static ImageLayout1Fragment newInstance() {
        ImageLayout1Fragment fragment = new ImageLayout1Fragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerImageLayout1Component //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .imageLayout1Module(new ImageLayout1Module(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_layout1, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        viewList.add(tv1);

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

    @OnClick({R.id.tv1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                toShowPop(0);
                break;
        }
    }

    private void toShowPop(int id) {
        eventMsg = new ToActivityMsg();
        eventMsg.showFontPop = true;
        eventMsg.viewId = id;
        eventMsg.fragment = 1;
        eventMsg.text = viewList.get(id).getText().toString();
        EventBusUtils.post(eventMsg);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusUtils.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.unregister(this);
    }

    private List<TextView> viewList = new ArrayList<>();

    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showMsg(ToFragmentMsg msg) {
        if (msg.fragment == 1) {
            if (msg.isFontOk) {
                viewList.get(msg.viewId).setText(msg.text);
                viewList.get(msg.viewId).setTextSize(msg.size);
                viewList.get(msg.viewId).setTextColor(getResources().getColor(msg.color));
            }
            if (msg.isAddText) {
                addTextView(msg);
            }
        }
    }

    private void addTextView(ToFragmentMsg msg) {
        TextView textView = new TextView(getActivity());
        textView.setText(msg.text);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(msg.size);
        textView.setTag(viewList.size());
        textView.setPadding(5, 5, 5, 5);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(

                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        textView.setLayoutParams(lp);
        rlWl.addView(textView);
        eventMsg = new ToActivityMsg();
        eventMsg.isViewId = true;
        eventMsg.viewId = viewList.size();
        EventBusUtils.post(eventMsg);
        viewList.add(textView);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("--", "点击了");

                toShowPop(viewList.size() - 1);
            }
        });
        moveView(textView);


    }

    private long firstTime = 0;
    private long secondTime;

    private void moveView(TextView textView) {
        textView.setOnTouchListener(new View.OnTouchListener() {
            int startx;//手指第一次点击屏幕时的位置x
            int starty;//手指第一次点击屏幕时的位置y
            int lastx;//手指在屏幕上抬起时的位置x
            int lasty;//手指在屏幕上抬起时的位置y
            int dx, dy;

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isclick = false;//当按下的时候设置isclick为false，具体原因看后边的讲解
                        firstTime = System.currentTimeMillis();
                        startx = (int) event.getRawX();
                        starty = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        secondTime = System.currentTimeMillis();
                        //当从点击到弹起小于半秒的时候,则判断为点击,如果超过则不响应点击事件
                        if ((secondTime - firstTime) < 0.3 * 1000L) {
                            isclick = false;
                        } else {
                            isclick = true;
                        }
                        lastx = textView.getLeft();
                        lasty = textView.getTop();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        isclick = true;//当按钮被移动的时候设置isclick为true
                        int x = (int) event.getRawX();
                        int y = (int) event.getRawY();
                        dx = x - startx;
                        dy = y - starty;
                        if (Math.abs(dx) > 10 || Math.abs(dy) > 10) {
                            textView.layout(textView.getLeft() + dx, textView.getTop() + dy, textView.getRight() + dx, textView.getBottom() + dy);
                            startx = (int) event.getRawX();
                            starty = (int) event.getRawY();
                            textView.invalidate();
                        }
                        break;
                }
                return isclick;
            }
        });
    }
}
