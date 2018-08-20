package zhonghuass.ssml.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerTradeDetailComponent;
import zhonghuass.ssml.di.module.TradeDetailModule;
import zhonghuass.ssml.mvp.contract.TradeDetailContract;
import zhonghuass.ssml.mvp.presenter.TradeDetailPresenter;
import zhonghuass.ssml.mvp.ui.adapter.MyPagerAdapter;
import zhonghuass.ssml.mvp.ui.fragment.CompanyBriefFragment;
import zhonghuass.ssml.mvp.ui.fragment.CompanyDanymicFragment;
import zhonghuass.ssml.mvp.ui.fragment.CompanyInviteFragment;
import zhonghuass.ssml.mvp.ui.fragment.CompanyRecommendFragment;
import zhonghuass.ssml.mvp.ui.fragment.DanymicFragment;
import zhonghuass.ssml.mvp.ui.fragment.FocusFragment;
import zhonghuass.ssml.mvp.ui.fragment.RecommendFragment;
import zhonghuass.ssml.utils.PrefUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class TradeDetailActivity extends BaseActivity<TradeDetailPresenter> implements TradeDetailContract.View {

    @BindView(R.id.top_bg)
    ImageView topBg;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_share)
    ImageView imgShare;
    @BindView(R.id.tv_focus)
    TextView tvFocus;
    @BindView(R.id.tv_fans)
    TextView tvFans;
    @BindView(R.id.tv_focus_num)
    TextView tvFocusNum;
    @BindView(R.id.tv_fans_num)
    TextView tvFansNum;
    @BindView(R.id.company_name)
    TextView companyName;
    @BindView(R.id.company_url)
    TextView companyUrl;
    @BindView(R.id.btn_to_focus)
    TextView btnToFocus;
    @BindView(R.id.company_indict)
    MagicIndicator companyIndict;
    @BindView(R.id.company_vp)
    ViewPager companyVp;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] mDataList = {"动态", "产品", "招聘", "简介"};
    private List<String> mTitle = Arrays.asList(mDataList);

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTradeDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .tradeDetailModule(new TradeDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_trade_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String eid = getIntent().getStringExtra("eid");
        if(eid==null){
            Toast.makeText(this, "eid=null", Toast.LENGTH_SHORT).show();
        }
        PrefUtils.putString(this,"eid",eid);
        fragments.add(CompanyDanymicFragment.newInstance());
        fragments.add(CompanyRecommendFragment.newInstance());
        fragments.add(CompanyInviteFragment.newInstance());
        fragments.add(CompanyBriefFragment.newInstance());
        // 创建ViewPager适配器
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        myPagerAdapter.setFragments(fragments);
        // 给ViewPager设置适配器
        companyVp.setAdapter(myPagerAdapter);
        companyVp.setOffscreenPageLimit(2);

        // MagicIndicator magicIndicator = (MagicIndicator) inflate.findViewById(R.id.my_indict);
        //   magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setFollowTouch(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int i) {
                ColorTransitionPagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mTitle.get(i));
                simplePagerTitleView.setTextSize(17);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.hui));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.text_c28));
                simplePagerTitleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                simplePagerTitleView.setOnClickListener((v) ->
                        companyVp.setCurrentItem(i, false)
                );
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(linePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(getResources().getColor(R.color.colorcf1313));
                return linePagerIndicator;
            }
        });
        companyIndict.setNavigator(commonNavigator);
        ViewPagerHelper.bind(companyIndict, companyVp);


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
