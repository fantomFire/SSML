package zhonghuass.ssml.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;

import butterknife.BindView;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerHomeFragmentComponent;
import zhonghuass.ssml.di.module.HomeFragmentModule;
import zhonghuass.ssml.mvp.contract.HomeFragmentContract;
import zhonghuass.ssml.mvp.presenter.HomeFragmentPresenter;
import zhonghuass.ssml.mvp.ui.adapter.MyPagerAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class HomeFragment extends BaseFragment<HomeFragmentPresenter> implements HomeFragmentContract.View {

    @BindView(R.id.my_indict)
    TabLayout myIndict;
    @BindView(R.id.frag_vp)
    ViewPager fragVp;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeFragmentComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .homeFragmentModule(new HomeFragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("home initview");
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        System.out.println("===initData");
        System.out.println("==================");
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add( RecommendFragment.newInstance());
        fragments.add( DanymicFragment.newInstance());
        fragments.add(FocusFragment.newInstance());
        // 创建ViewPager适配器
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        myPagerAdapter.setFragments(fragments);
        // 给ViewPager设置适配器
        fragVp.setAdapter(myPagerAdapter);

        // TabLayout 指示器 (记得自己手动创建4个Fragment,注意是 app包下的Fragment 还是 V4包下的 Fragment)
        myIndict.addTab(myIndict.newTab());
        myIndict.addTab(myIndict.newTab());
        myIndict.addTab(myIndict.newTab());
        // 使用 TabLayout 和 ViewPager 相关联
        myIndict.setupWithViewPager(fragVp);
        // TabLayout指示器添加文本
        myIndict.getTabAt(0).setText("推荐");
        myIndict.getTabAt(1).setText("动态");
        myIndict.getTabAt(2).setText("关注");
        System.out.println("--------------"+fragments.size());
    }

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

}
