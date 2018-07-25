package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.github.library.layoutView.BottomNavigationViewEx;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerMainActivityComponent;
import zhonghuass.ssml.di.module.MainActivityModule;
import zhonghuass.ssml.mvp.contract.MainActivityContract;
import zhonghuass.ssml.mvp.presenter.MainActivityPresenter;
import zhonghuass.ssml.mvp.ui.fragment.CompanyFragment;
import zhonghuass.ssml.mvp.ui.fragment.DialyFragment;
import zhonghuass.ssml.mvp.ui.fragment.HomeFragment;
import zhonghuass.ssml.mvp.ui.fragment.MycenterFragment;
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
    private List<Integer> mTitles;
    private List<Fragment> mFragments;
    private List<Integer> mNavIds;
    private int mReplace = 0;

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
        setContentView(R.layout.activity_main);
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        //关闭菜单切换动画特效
        bottomMenu.enableAnimation(false);
        bottomMenu.enableShiftingMode(false);
        bottomMenu.enableItemShiftingMode(false);

        toolbarBack.setVisibility(View.GONE);
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
    }

    private BottomNavigationView.OnNavigationItemSelectedListener menuSelect = item -> {
        switch (item.getItemId()) {
//            case R.id.bottom_menu1:
//                mReplace = 0;
//                changeFragment();
//                return true; //不返回图标不变色
//            case R.id.bottom_menu2:
//                mReplace = 1;
//                changeFragment();
//                return true; //不返回图标不变色
//            case R.id.bottom_menu3:
//                mReplace = 2;
//                changeFragment();
//                return true; //不返回图标不变色
//            case R.id.bottom_menu4:
//                mReplace = 3;
//                changeFragment();
//                return true; //不返回图标不变色
        }
        return false;
    };

    private void changeFragment() {
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

}
