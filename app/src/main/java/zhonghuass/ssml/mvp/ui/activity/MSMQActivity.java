package zhonghuass.ssml.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerMSMQComponent;
import zhonghuass.ssml.di.module.MSMQModule;
import zhonghuass.ssml.mvp.contract.MSMQContract;
import zhonghuass.ssml.mvp.presenter.MSMQPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.mvp.ui.adapter.MyPagerAdapter;
import zhonghuass.ssml.mvp.ui.fragment.DanymicFragment;
import zhonghuass.ssml.mvp.ui.fragment.FocusFragment;
import zhonghuass.ssml.mvp.ui.fragment.RecommendFragment;
import zhonghuass.ssml.mvp.ui.fragment.RelatedFragment;
import zhonghuass.ssml.mvp.ui.fragment.SystemFragment;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MSMQActivity extends MBaseActivity<MSMQPresenter> implements MSMQContract.View {
    private static final String[] MSMQ = new String[]{"与我相关", "系统消息"};
    @BindView(R.id.magic_msmq)
    MagicIndicator magicMsmq;
    @BindView(R.id.vp_msmq)
    ViewPager vpMsmq;
    private List<String> mMSMQList= Arrays.asList(MSMQ);;
    private ArrayList<Fragment> fragments=new ArrayList<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMSMQComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mSMQModule(new MSMQModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_msmq; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolBar("消息");
        fragments.add(RelatedFragment.newInstance());
        fragments.add(SystemFragment.newInstance());
        // 创建ViewPager适配器
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        myPagerAdapter.setFragments(fragments);
        // 给ViewPager设置适配器
        vpMsmq.setAdapter(myPagerAdapter);
        initMagicIndicator();
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

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mMSMQList == null ? 0 : mMSMQList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mMSMQList.get(index));
                simplePagerTitleView.setTextSize(17);
                simplePagerTitleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                simplePagerTitleView.setNormalColor(Color.parseColor("#282828"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#EE2C2C"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vpMsmq.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(indicator.MODE_WRAP_CONTENT);
                indicator.setColors(Color.parseColor("#EE2C2C"));
                return indicator;
            }
        });
        magicMsmq.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicMsmq, vpMsmq);
    }
}
