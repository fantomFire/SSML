package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
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
    private ImagesAdapter imagesAdapter;
    private Intent intent;

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";
    private ArrayList<LocalMedia> imagesList;

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
        imagesList = intent.getParcelableArrayListExtra("uploadinfo");
        Uri url = Uri.parse(ANDROID_RESOURCE + getPackageName() + FOREWARD_SLASH + R.mipmap.fb_icon_12);
            LocalMedia localMedia = new LocalMedia();
            localMedia.setPath(url.toString());
            imagesList.add(localMedia);

        imgRec.setLayoutManager(new GridLayoutManager(this, 3));
        imagesAdapter = new ImagesAdapter(R.layout.image_item, imagesList);
        imgRec.setAdapter(imagesAdapter);
        imagesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if(imagesList.size()<10){
                    if(position== imagesList.size()-1){
                        selectImages(10- imagesList.size());

                    }
                }
            }
        });

    }

    private void selectImages(int count) {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(count)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                //  .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                // .glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                // .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                //.isGif(false)// 是否显示gif图片 true or false
                //.compressSavePath(getPath())//压缩图片保存地址
                //.freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(true)// 是否开启点击声音 true or false
                //.selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                // .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
               // .videoQuality(1)// 视频录制质量 0 or 1 int
               // .videoMaxSecond(150)// 显示多少秒以内的视频or音频也可适用 int
              //  .recordVideoSecond(60)//视频秒数录制 默认60s int
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(1);//结果回调onActivityResult code


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode == 1){
            if(data!=null){
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                imagesList.addAll(imagesList.size()-1,selectList);
                imagesAdapter.notifyDataSetChanged();
            }
        }


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

}
