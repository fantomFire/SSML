package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jude.rollviewpager.RollPagerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerGraphicDetailsComponent;
import zhonghuass.ssml.di.module.GraphicDetailsModule;
import zhonghuass.ssml.mvp.contract.GraphicDetailsContract;
import zhonghuass.ssml.mvp.model.appbean.GraphicBean;
import zhonghuass.ssml.mvp.presenter.GraphicDetailsPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.utils.CircleImageView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class GraphicDetailsActivity extends MBaseActivity<GraphicDetailsPresenter> implements GraphicDetailsContract.View {

    @BindView(R.id.vp_banner)
    RollPagerView vpBanner;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.civ_icon1)
    CircleImageView civIcon1;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.btn_focus)
    Button btnFocus;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_site)
    TextView tvSite;
    private String content_id="71",member_id="1", member_type="0";
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGraphicDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .graphicDetailsModule(new GraphicDetailsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_graphic_details; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.getGraphicData(content_id,member_id, member_type);
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
    public void showGraphicData(GraphicBean data) {
        System.out.println(data.getContent_title());
    }
}
