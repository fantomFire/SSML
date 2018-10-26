package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.library.baseAdapter.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jude.rollviewpager.RollPagerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerGraphicDetailsComponent;
import zhonghuass.ssml.di.module.GraphicDetailsModule;
import zhonghuass.ssml.mvp.contract.GraphicDetailsContract;
import zhonghuass.ssml.mvp.model.appbean.DiscussBean;
import zhonghuass.ssml.mvp.model.appbean.GraphicBean;
import zhonghuass.ssml.mvp.presenter.GraphicDetailsPresenter;
import zhonghuass.ssml.mvp.ui.adapter.DiscussAdapter;
import zhonghuass.ssml.mvp.ui.adapter.StorePagerAdapter;
import zhonghuass.ssml.utils.CircleImageView;
import zhonghuass.ssml.utils.Constants;
import zhonghuass.ssml.utils.PrefUtils;

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
    @BindView(R.id.img_collect)
    ImageView imgCollect;
    @BindView(R.id.img_like)
    ImageView imgLike;
    @BindView(R.id.ll_pop)
    LinearLayout llPop;

    @BindView(R.id.collect_num)
    TextView collectNum;
    @BindView(R.id.ll_collect)
    LinearLayout llCollect;
    @BindView(R.id.dicuss_num)
    TextView dicussNum;
    @BindView(R.id.ll_discuss)
    LinearLayout llDiscuss;
    @BindView(R.id.share_num)
    TextView shareNum;
    @BindView(R.id.ll_share)
    LinearLayout llShare;
    @BindView(R.id.like_num)
    TextView likeNum;
    @BindView(R.id.ll_like)
    LinearLayout llLike;
    private int page = 1;
    private String content_id, member_id, member_type;
    private StorePagerAdapter storePagerAdapter;
    private List<DiscussBean> mList = new ArrayList<>();
    private DiscussAdapter discussAdapter;
    private String user_id;
    private String user_type;
    private PopupWindow popupWindow;
    private SwipeRefreshLayout swipe;
    private RecyclerView disRecycle;

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

        user_id = PrefUtils.getString(this, Constants.USER_ID, "");
        user_type = PrefUtils.getString(this, Constants.MEMBER_TYPE, "1");
        Intent intent = getIntent();
        content_id = intent.getStringExtra("content_id");
        member_id = intent.getStringExtra("member_id");
        member_type = intent.getStringExtra("member_type");
        mPresenter.getGraphicData(content_id, user_id, user_type);

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
        ArmsUtils.makeText(this, message);
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

        Glide.with(this)
                .load(data.getMember_image())
                .into(civIcon1);
        tvCompany.setText(data.getMember_name());
        tvDate.setText(data.getAdd_time());
        tvTitle.setText(data.getContent_title());
        tvContent.setText(data.getContent_detail());
        tvSite.setText(data.getContent_position());
        collectNum.setText(data.getAmount_of_collection());
        dicussNum.setText(data.getAmount_of_comment());
        shareNum.setText(data.getAmount_of_forward());
        likeNum.setText(data.getAmount_of_praise());
        //设置轮播图数据
        storePagerAdapter = new StorePagerAdapter(this, data.getContent_images());
        vpBanner.setPlayDelay(3000);
        vpBanner.setAnimationDurtion(500);
        vpBanner.setAdapter(storePagerAdapter);
        boolean praise_tag = data.isPraise_tag();
        boolean collection_tag = data.isCollection_tag();
        boolean concern_tag = data.isConcern_tag();
        if (praise_tag) {
            imgLike.setBackgroundResource(R.mipmap.ml_icon_16);
        }
        if (collection_tag) {
            imgCollect.setBackgroundResource(R.mipmap.sc);
        }
        if(concern_tag){
            btnFocus.setText("已关注");
        }
    }

    @Override
    public void showDiscussData(List<DiscussBean> data) {
        if (swipe.isRefreshing()) {
            swipe.setRefreshing(false);
        }

        discussAdapter.loadMoreComplete();
        if (page > 1) {
            mList.addAll(data);
            discussAdapter.addData(data);
        } else {
            mList = data;
            discussAdapter.setNewData(data);
        }


    }

    @Override
    public void showPopState() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        mPresenter.getDiscussList(content_id, user_id, user_type, page);
    }

    @Override
    public void notifystate() {
        discussAdapter.loadMoreEnd(true);
        showMessage("没有更多数据,请稍后尝试!");
    }

    @Override
    public void ContentState(int position) {
        mList.get(position).setPraise_tag(true);
        discussAdapter.notifyDataSetChanged();
    }

    @Override
    public void getNewData() {
        mPresenter.getGraphicData(content_id, user_id, user_type);
    }


    @OnClick({R.id.img_like, R.id.iv_back, R.id.btn_focus, R.id.ll_collect, R.id.ll_discuss, R.id.ll_share, R.id.ll_like})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_focus:

                if (checkIfUpload()) {
                    mPresenter.addFocus(user_id, user_type, member_id, member_type);
                } else {
                    ArmsUtils.startActivity(LogInActivity.class);
                }

                break;
            case R.id.ll_discuss:
                showPopowindow();
                break;

            case R.id.ll_collect:
                if (checkIfUpload()) {
                    mPresenter.addCollect(user_id, content_id, user_type);
                } else {
                    ArmsUtils.startActivity(LogInActivity.class);
                }
                break;
            case R.id.ll_like:
                if (checkIfUpload()) {
                    mPresenter.addLike(user_id, content_id, user_type);
                } else {
                    ArmsUtils.startActivity(LogInActivity.class);
                }

                break;

            case R.id.ll_share:
                break;
        }
    }


    private void showPopowindow() {

        mPresenter.getDiscussList(content_id, user_id, user_type, page);


        popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        int screenHeidth = ArmsUtils.getScreenHeidth(this);
        popupWindow.setHeight(screenHeidth / 5 * 3);
        //  View popView = LayoutInflater.from(this).inflate(R.layout.layout_discuss, null);
        View popView = LayoutInflater.from(this).inflate(R.layout.layout_discuss_item, null);
        popupWindow.setContentView(popView);
        //获取评论内容

        popupWindow.setFocusable(true);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.showAtLocation(llPop, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);


        swipe = popView.findViewById(R.id.discuss_swi);
        disRecycle = popView.findViewById(R.id.dis_rec);
        EditText mEdit = popView.findViewById(R.id.et_context);
        TextView tvpublish = popView.findViewById(R.id.tv_publish);


        mEdit.setFocusableInTouchMode(true);
        mEdit.requestFocus();
        mEdit.setFocusable(true);


        disRecycle.setLayoutManager(new LinearLayoutManager(this));
        discussAdapter = new DiscussAdapter(R.layout.discuss_item, mList);
        disRecycle.setAdapter(discussAdapter);


        discussAdapter.setOnLoadMoreListener(() -> {
            page++;
            mPresenter.getDiscussList(content_id, user_id, user_type, page);
        });
        swipe.setOnRefreshListener(() -> {
            page = 1;
            //   discussAdapter.isLoadMoreEnable();
            mPresenter.getDiscussList(content_id, user_id, user_type, page);

        });
        discussAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ll_tolike:
                        String comment_id = mList.get(position).getComment_id();
                        addContentLike(comment_id, position);

                        break;
                }
            }
        });
        tvpublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mContext = mEdit.getText().toString().trim();

                toPublishContext(mContext);
            }
        });


    }

    private void addContentLike(String comment_id, int position) {
        if (checkIfUpload()) {
            mPresenter.addContentLike(user_id, user_type, comment_id, position);
        } else {
            ArmsUtils.startActivity(LogInActivity.class);
        }

    }

    private void toPublishContext(String mContext) {
        if (checkIfUpload()) {
            if (TextUtils.isEmpty(mContext)) {
                Toast.makeText(this, "说点啥...", Toast.LENGTH_SHORT).show();
                return;
            }
            mPresenter.publishContext(user_id, member_type, content_id, mContext);
        } else {
            ArmsUtils.startActivity(LogInActivity.class);
        }
    }

    private boolean checkIfUpload() {

        String member_id = PrefUtils.getString(this, Constants.USER_ID, "");
        if (member_id.equals("")) {

            return false;

        }
        return true;
    }
}
