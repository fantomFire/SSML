package zhonghuass.ssml.mvp.ui.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.library.layoutView.CircleImageView;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import zhonghuass.ssml.di.component.DaggerMycenterComponent;
import zhonghuass.ssml.di.module.MycenterModule;
import zhonghuass.ssml.mvp.EventMsg;
import zhonghuass.ssml.mvp.contract.MycenterContract;
import zhonghuass.ssml.mvp.model.appbean.UserInfoBean;
import zhonghuass.ssml.mvp.presenter.MycenterPresenter;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.ui.activity.LogInActivity;
import zhonghuass.ssml.mvp.ui.activity.MyConcernActivity;
import zhonghuass.ssml.mvp.ui.activity.MyFansActivity;
import zhonghuass.ssml.mvp.ui.activity.MyInfoActivity;
import zhonghuass.ssml.mvp.ui.activity.PicEditActivity;
import zhonghuass.ssml.mvp.ui.adapter.ViewPagerAdapter;
import zhonghuass.ssml.utils.ACache;
import zhonghuass.ssml.utils.Constants;
import zhonghuass.ssml.utils.EventBusUtils;
import zhonghuass.ssml.utils.PrefUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MycenterFragment extends BaseFragment<MycenterPresenter> implements MycenterContract.View {
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.iv_photo)
    CircleImageView mPhoto;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_setting)
    TextView ivSetting;
    @BindView(R.id.ll_concern)
    LinearLayout llConcern;
    @BindView(R.id.ll_fans)
    LinearLayout llFans;
    @BindView(R.id.ll_good)
    LinearLayout llGood;
    @BindView(R.id.tv_concern)
    TextView tvConcern;
    @BindView(R.id.tv_fans)
    TextView tvFans;
    @BindView(R.id.tv_good)
    TextView tvGood;
    private List<Fragment> fragments = new ArrayList<>();
    private ViewPagerAdapter mAdapter;
    private String[] titles = {"图文", "视频"};
    private PhotoFragment photoFragment;

    public static MycenterFragment newInstance() {
        MycenterFragment fragment = new MycenterFragment();
        return fragment;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mTabLayout.post(new Runnable() {
                @Override
                public void run() {
                    setIndicator(mTabLayout, 70, 70);
                }
            });
        }
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMycenterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mycenterModule(new MycenterModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mycenter, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
//        initInfo();
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

    private void initInfo() {
        UserInfoBean mUserInfo = (UserInfoBean) ACache.get(getActivity()).getAsObject(Constants.USERINFO);
        tvName.setText(mUserInfo.member_name);
        Glide.with(this).load(mUserInfo.member_image).into(mPhoto);
        tvConcern.setText(mUserInfo.amount_of_concern);
        tvFans.setText(mUserInfo.amount_of_vermicelli);
        tvGood.setText(mUserInfo.amount_of_praise);
        tvConcern.setText(mUserInfo.amount_of_concern);

        Log.e("--","photo： "+mUserInfo.member_image);
        mTabLayout.getTabAt(0).setText("图文(" + mUserInfo.content_image_text_num + ")");
        mTabLayout.getTabAt(1).setText("视频(" + mUserInfo.content_video_num + ")");
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArmsUtils.startActivity(MyInfoActivity.class);
            }
        });
        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArmsUtils.startActivity(MyInfoActivity.class);
            }
        });
        photoFragment = PhotoFragment.newInstance();
        fragments.add(photoFragment);


        fragments.add(VideoFragment.newInstance());
        mAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通知MainActivty展示mNavigationView
                EventMsg msg = new EventMsg();
                msg.isShowNav = true;
                EventBusUtils.post(msg);
            }
        });

        llConcern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArmsUtils.startActivity(MyConcernActivity.class);
            }
        });
        llFans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArmsUtils.startActivity(MyFansActivity.class);
            }
        });
    }

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }


    }

    @Override
    public void setData(@Nullable Object data) {
        //通知图文模块加载
        // 通知PhotoFragment是来查询我的图文的
        EventMsg msg = new EventMsg();
        msg.tag = 1;
        msg.tId = PrefUtils.getString(getActivity(), "eid", "1");
        photoFragment.setData(msg);
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showMInfo(EventMsg eventMsg) {
        if (eventMsg != null && eventMsg.isShowInfo) {
            initInfo();
        }
    }
}
