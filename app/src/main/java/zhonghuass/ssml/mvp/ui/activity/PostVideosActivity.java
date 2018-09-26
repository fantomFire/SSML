package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.library.baseAdapter.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerPostVideosComponent;
import zhonghuass.ssml.di.module.PostVideosModule;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.PostVideosContract;
import zhonghuass.ssml.mvp.model.appbean.IniviteBean;
import zhonghuass.ssml.mvp.presenter.PostVideosPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.mvp.ui.adapter.ImagesAdapter;
import zhonghuass.ssml.mvp.ui.adapter.PostVideoAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class PostVideosActivity extends MBaseActivity<PostVideosPresenter> implements PostVideosContract.View {

    @BindView(R.id.rvGrid)
    RecyclerView rvGrid;
    @BindView(R.id.image_up)
    ImageView imageUp;
    @BindView(R.id.eara_next)
    ImageView earaNext;
    @BindView(R.id.dialy_next)
    ImageView dialyNext;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_eara)
    TextView tvEara;
    @BindView(R.id.tv_tag)
    TextView tvTag;
    @BindView(R.id.img_rec)
    RecyclerView imgRec;
    private List<IniviteBean.ListBean> list;
    private PostVideoAdapter adapter;
    private String mediaPath;
    private String userEare;
    private String dailyTag;
    private List<String> mList = new ArrayList<>();
    private String selectType;
    private List<String> imagesList = new ArrayList<>();
    private ImagesAdapter imagesAdapter;
    private Intent intent;

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPostVideosComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .postVideosModule(new PostVideosModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_post_videos; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("发布");
        intent = getIntent();

        selectType = intent.getStringExtra("selectType");

        System.out.println("mediaPath" + mediaPath);
        if (selectType.equals("editMedia")) {

            imageUp.setVisibility(View.VISIBLE);
            imgRec.setVisibility(View.GONE);

            mediaPath = intent.getStringExtra("mediaPath");

            //设置图片
            if (mediaPath != null) {
                setImage(mediaPath);
            }

            imageUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PostVideosActivity.this, MediaPlayerActivity.class);
                    intent.putExtra("mediaPath", mediaPath);
                    startActivity(intent);
                }
            });
        } else if (selectType.equals("multipleImage")) {
            //初始化多图
            initImages();

        }


        //初始化每日一语
        initDialySay();

        mPresenter.getInviteData("1", 1, 5);
    }

    private void initImages() {


        imageUp.setVisibility(View.GONE);
        imgRec.setVisibility(View.VISIBLE);
        ArrayList<LocalMedia> imagesList = intent.getParcelableArrayListExtra("uploadinfo");
        Uri url = Uri.parse(ANDROID_RESOURCE + getPackageName() + FOREWARD_SLASH + R.mipmap.fb_icon_12);
            LocalMedia localMedia = new LocalMedia();
            localMedia.setPath(url.toString());
            imagesList.add(localMedia);

        imgRec.setLayoutManager(new GridLayoutManager(this, 3));
        imagesAdapter = new ImagesAdapter(R.layout.image_item, imagesList);
        imgRec.setAdapter(imagesAdapter);

    }

    private void initDialySay() {
        rvGrid.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new PostVideoAdapter(R.layout.postvideos_item, list);
        rvGrid.setAdapter(adapter);
        rvGrid.setNestedScrollingEnabled(false);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                tvTag.setText(position + "");
            }
        });
    }

    private void setImage(String mediaPath) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(mediaPath);
        Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime();
        imageUp.setImageBitmap(bitmap);
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
    public void showdata(BaseResponse<IniviteBean> iniviteBeanBaseResponse) {
        list = iniviteBeanBaseResponse.getData().list;
        adapter.addData(list);
    }


    @OnClick({R.id.eara_next, R.id.dialy_next, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.eara_next:
                break;
            case R.id.dialy_next:
                break;
            case R.id.tv_right:
                checkData();

                break;
        }
    }

    private void checkData() {
        String mContent = etContent.getText().toString();
        mList.add(mediaPath);
        mPresenter.upLoadData(mList, mContent, userEare, dailyTag);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
