package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.library.layoutView.BottomNavigationViewEx;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerMainActivityComponent;
import zhonghuass.ssml.di.module.MainActivityModule;
import zhonghuass.ssml.mvp.EventMsg;
import zhonghuass.ssml.mvp.contract.MainActivityContract;
import zhonghuass.ssml.mvp.presenter.MainActivityPresenter;
import zhonghuass.ssml.mvp.ui.fragment.CompanyFragment;
import zhonghuass.ssml.mvp.ui.fragment.DialyFragment;
import zhonghuass.ssml.mvp.ui.fragment.HomeFragment;
import zhonghuass.ssml.mvp.ui.fragment.MycenterFragment;
import zhonghuass.ssml.utils.EventBusUtils;
import zhonghuass.ssml.utils.FragmentUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static zhonghuass.ssml.utils.EventBusTags.ACTIVITY_FRAGMENT_REPLACE;


public class MainActivity extends BaseActivity<MainActivityPresenter> implements MainActivityContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_frame)
    FrameLayout mainFrame;
    @BindView(R.id.bottomMenu)
    BottomNavigationViewEx bottomMenu;
    @BindView(R.id.navigation)
    LinearLayout mNavigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.tv_menu1)
    TextView tvmenu1;
    @BindView(R.id.tv_menu2)
    TextView tvmenu2;
    @BindView(R.id.tv_menu3)
    TextView tvmenu3;
    @BindView(R.id.tv_menu4)
    TextView tvmenu4;
    @BindView(R.id.tv_menu5)
    TextView tvmenu5;
    @BindView(R.id.tv_menu6)
    TextView tvmenu6;
    private List<Integer> mTitles;
    private List<Fragment> mFragments;
    private List<Integer> mNavIds;
    private int mReplace = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusUtils.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtils.unregister(this);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainActivityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);
    }


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public boolean useEventBus() {
        return super.useEventBus();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //关闭菜单切换动画特效
        bottomMenu.enableAnimation(false);
        bottomMenu.enableShiftingMode(false);
        bottomMenu.enableItemShiftingMode(false);

        toolbarBack.setVisibility(View.GONE);
        toolbar.setVisibility(View.GONE);
        //  mPresenter.requestPermissions();
        if (mTitles == null) {
            mTitles = new ArrayList<>();
            mTitles.add(R.string.tab_home);
            mTitles.add(R.string.tab_company);
            mTitles.add(R.string.tab_daily);
            mTitles.add(R.string.tab_mycenter);
        }
        if (mNavIds == null) {
            mNavIds = new ArrayList<>();
            mNavIds.add(R.id.tab_home);
            mNavIds.add(R.id.tab_company);
            mNavIds.add(R.id.tab_daily);
            mNavIds.add(R.id.tab_mycenter);
        }

        HomeFragment homeFragment;
        CompanyFragment companyFragment;
        DialyFragment dialyFragment;
        MycenterFragment mycenterFragment;

        if (savedInstanceState == null) {
            homeFragment = HomeFragment.newInstance();
            companyFragment = CompanyFragment.newInstance();
            dialyFragment = DialyFragment.newInstance();
            mycenterFragment = MycenterFragment.newInstance();
        } else {
            mReplace = savedInstanceState.getInt(ACTIVITY_FRAGMENT_REPLACE);
            FragmentManager fm = getSupportFragmentManager();
            homeFragment = (HomeFragment) FragmentUtils.findFragment(fm, HomeFragment.class);
            companyFragment = (CompanyFragment) FragmentUtils.findFragment(fm, CompanyFragment.class);
            dialyFragment = (DialyFragment) FragmentUtils.findFragment(fm, DialyFragment.class);
            mycenterFragment = (MycenterFragment) FragmentUtils.findFragment(fm, MycenterFragment.class);
        }
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            mFragments.add(homeFragment);
            mFragments.add(companyFragment);
            mFragments.add(dialyFragment);
            mFragments.add(mycenterFragment);
        }
        FragmentUtils.addFragments(getSupportFragmentManager(), mFragments, R.id.main_frame, 0);
        toolbarTitle.setText(mTitles.get(0));//设置默认显示第一个Fragment标题

        bottomMenu.setOnNavigationItemSelectedListener(menuSelect);


        //底部菜单栏图标字体点击颜色变化在这里修改
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        };
        int[] colors = new int[]{getResources().getColor(R.color.corlor28),
                getResources().getColor(R.color.colorcf1313)
        };
        ColorStateList csl = new ColorStateList(states, colors);
        bottomMenu.setItemTextColor(csl);
        bottomMenu.setItemIconTintList(csl);


        // 设置NavigationView宽度
        ViewGroup.LayoutParams params = mNavigationView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels * 2 / 3;
        mNavigationView.setLayoutParams(params);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener menuSelect = item -> {
        switch (item.getItemId()) {
            case R.id.bottom_menu1:
                mReplace = 0;
                changeFragment();
                return true; //不返回图标不变色
            case R.id.bottom_menu2:
                mReplace = 1;
                changeFragment();
                return true; //不返回图标不变色
            case R.id.bottom_menu3:
                mReplace = 2;
                changeFragment();
                return true; //不返回图标不变色
            case R.id.bottom_menu4:
                mReplace = 3;
                changeFragment();
                return true; //不返回图标不变色
        }
        return false;
    };

    private void changeFragment() {
        if (mReplace == 3 || mReplace == 0) {
            //隐藏标题栏
            toolbar.setVisibility(View.GONE);
        } else {
            toolbar.setVisibility(View.VISIBLE);
        }
        toolbarTitle.setText(mTitles.get(mReplace));
        FragmentUtils.hideAllShowFragment(mFragments.get(mReplace));
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ACTIVITY_FRAGMENT_REPLACE, mReplace);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showNavigation(EventMsg eventMsg) {
        if (eventMsg != null && eventMsg.isShowNav) {
            mDrawerLayout.openDrawer(mNavigationView);
        }
    }

    private double firstTime = 0;

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            ArmsUtils.snackbarText("再按一次退出程序");
            firstTime = secondTime;
        } else {
            ArmsUtils.exitApp();
        }
    }


    @OnClick({R.id.civ_photo, R.id.tv_menu1, R.id.tv_menu2, R.id.tv_menu3, R.id.tv_menu4, R.id.tv_menu5, R.id.tv_menu6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.civ_photo:
                ArmsUtils.startActivity(MyInfoActivity.class);
                break;
            case R.id.tv_menu1:
                Log.e("--", "1");
                break;
            case R.id.tv_menu2:
                Log.e("--", "2");
                break;
            case R.id.tv_menu4:
                ArmsUtils.startActivity(RealNameActivityActivity.class);
                break;
            case R.id.tv_menu5:
                ArmsUtils.startActivity(HelpActivityActivity.class);
                break;
            case R.id.tv_menu6:
                ArmsUtils.startActivity(MySettingActivityActivity.class);
                break;
        }
    }
}
