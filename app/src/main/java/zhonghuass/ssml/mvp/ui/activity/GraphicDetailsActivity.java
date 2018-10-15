package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jude.rollviewpager.RollPagerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerGraphicDetailsComponent;
import zhonghuass.ssml.di.module.GraphicDetailsModule;
import zhonghuass.ssml.mvp.contract.GraphicDetailsContract;
import zhonghuass.ssml.mvp.model.appbean.GraphicBean;
import zhonghuass.ssml.mvp.presenter.GraphicDetailsPresenter;
import zhonghuass.ssml.mvp.ui.adapter.StorePagerAdapter;
import zhonghuass.ssml.utils.CircleImageView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class GraphicDetailsActivity extends BaseActivity<GraphicDetailsPresenter> implements GraphicDetailsContract.View {

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
    private String content_id = "71", member_id = "1", member_type = "0";
    private StorePagerAdapter storePagerAdapter;

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


        Intent intent = getIntent();
      /*   content_id = intent.getStringExtra("content_id");
        member_id = intent.getStringExtra("member_id");
        member_type = intent.getStringExtra("member_type");
*/
        System.out.println("content_id" + content_id + "   member_id" + member_id);
        mPresenter.getGraphicData("71", "1", "0");
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
    public void showGraphicData(GraphicBean.DataBean data) {
        System.out.println("wwwwwwwww" + data.getTheme_title());
        Glide.with(this)
                .load(data.getMember_image())
                .into(civIcon1);
        tvCompany.setText(data.getMember_name());
        tvDate.setText(data.getAdd_time());
        tvTitle.setText(data.getContent_title());
        tvContent.setText(data.getContent_detail());
        tvSite.setText(data.getContent_position());
        //设置轮播图数据
        storePagerAdapter = new StorePagerAdapter(this, data.getContent_images());
        vpBanner.setPlayDelay(3000);
        vpBanner.setAnimationDurtion(500);
        vpBanner.setAdapter(storePagerAdapter);

    }


    @OnClick({R.id.vp_banner, R.id.iv_back, R.id.iv_right, R.id.civ_icon1, R.id.btn_focus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vp_banner:
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_right:
                break;
            case R.id.civ_icon1:
                break;
            case R.id.btn_focus:
                break;
        }
    }
}
