package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import qdx.bezierviewpager_compile.BezierRoundView;
import qdx.bezierviewpager_compile.util.ImageLoadFactory;
import qdx.bezierviewpager_compile.vPage.BezierViewPager;
import qdx.bezierviewpager_compile.vPage.CardPagerAdapter;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerPublishComponent;
import zhonghuass.ssml.di.module.PublishModule;
import zhonghuass.ssml.mvp.contract.PublishContract;
import zhonghuass.ssml.mvp.presenter.PublishPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.utils.CustomDialog;
import zhonghuass.ssml.utils.CustomSelectDialog;
import zhonghuass.ssml.utils.GlideImageClient;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class PublishActivity extends BaseActivity<PublishPresenter> implements PublishContract.View {
    public static PublishActivity publishActivity;
    @BindView(R.id.view_page)
    BezierViewPager viewPage;
    @BindView(R.id.bezRound)
    BezierRoundView bezRound;
    @BindView(R.id.tv_start)
    TextView tvStart;
    private List<Object> imgList = new ArrayList<>();
    private  int TEMPLATE_NUM = 0;
    private View inflate;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPublishComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .publishModule(new PublishModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {

        return R.layout.activity_publish; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        publishActivity = this;
        imgList.add("http://video.zhonghuass.cn/public/uploadfile/tmp/tx2.png");
        imgList.add("http://video.zhonghuass.cn/public/uploadfile/tmp/tx1.jpg");
        imgList.add("http://video.zhonghuass.cn/public/admin/images/tx.jpg");
        imgList.add("http://video.zhonghuass.cn/public/uploadfile/tmp/tx4.png");
        imgList.add("http://video.zhonghuass.cn/public/uploadfile/tmp/tx3.png");
        imgList.add("http://video.zhonghuass.cn/public/admin/images/tx.jpg");

        ImageLoadFactory.getInstance().setImageClient(new GlideImageClient());
        CardPagerAdapter cardAdapter = new CardPagerAdapter(this);
        cardAdapter.addImgUrlList(imgList);  //放置图片url的list，v1.0.3版本imgList集合类型为List<Obj>，只要Glide支持的都可以加载

        BezierViewPager viewPager = (BezierViewPager) findViewById(R.id.view_page);
        viewPager.setAdapter(cardAdapter);

        BezierRoundView bezRound = (BezierRoundView) findViewById(R.id.bezRound);
        bezRound.attach2ViewPage(viewPager);

        //设置阴影大小，即vPage  左右两个图片相距边框  maxFactor + 0.3*CornerRadius   *2
        //设置阴影大小，即vPage 上下图片相距边框  maxFactor*1.5f + 0.3*CornerRadius
        int mWidth = getWindowManager().getDefaultDisplay().getWidth();
        float heightRatio = 1.265f;  //高是宽的 0.565 ,根据图片比例
        int maxFactor = mWidth / 25;
        cardAdapter.setMaxElevationFactor(maxFactor);

        int mWidthPading = mWidth / 8;

        //因为我们adapter里的cardView CornerRadius已经写死为10dp，所以0.3*CornerRadius=3
        //设置Elevation之后，控件宽度要减去 (maxFactor + dp2px(3)) * heightRatio
        //heightMore 设置Elevation之后，控件高度 比  控件宽度* heightRatio  多出的部分
        float heightMore = (1.5f * maxFactor + dp2px(3)) - (maxFactor + dp2px(3)) * heightRatio;
        int mHeightPading = (int) (mWidthPading * heightRatio - heightMore);

        viewPager.setLayoutParams(new RelativeLayout.LayoutParams(mWidth, (int) (mWidth * heightRatio)));
        viewPager.setPadding(mWidthPading, mHeightPading, mWidthPading, mHeightPading);
        viewPager.setClipToPadding(false);
        viewPager.setAdapter(cardAdapter);
        viewPager.showTransformer(0.1f);

        cardAdapter.setOnCardItemClickListener(new CardPagerAdapter.OnCardItemClickListener() {
            @Override
            public void onClick(int i) {
                TEMPLATE_NUM=i;
                selectPhoto(PictureMimeType.ofImage(),1);
            }
        });
        // ImageView iv_bg = (ImageView) findViewById(R.id.iv_bg);
        // iv_bg.setLayoutParams(new RelativeLayout.LayoutParams(mWidth, (int) ((mWidth * heightRatio) + dp2px(60))));

    }

    public int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
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

    @OnClick(R.id.tv_start)
    public void onViewClicked() {

        inflate = LayoutInflater.from(this).inflate(R.layout.select_type, null);
        final CustomSelectDialog dialog = new CustomSelectDialog(this,0, 0, inflate, R.style.MyDialog);
        dialog.show();
        inflate.findViewById(R.id.img_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto(PictureMimeType.ofImage(),3);
                dialog.dismiss();
            }
        });

        inflate.findViewById(R.id.media_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto(PictureMimeType.ofVideo(),2);
                dialog.dismiss();
            }
        });
      //  ArmsUtils.startActivity(ImageEditorActivity.class);

    }

    private void selectPhoto(int type,int request_code) {
        PictureSelector.create(PublishActivity.this)
                .openGallery(type)//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(5)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(true)// 是否可预览视频 true or false
                .enablePreviewAudio(true) // 是否可播放音频 true or false
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
                .isGif(false)// 是否显示gif图片 true or false
                //.compressSavePath(getPath())//压缩图片保存地址
                //.freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                //.selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
               // .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .videoQuality(1)// 视频录制质量 0 or 1 int
                .videoMaxSecond(150)// 显示多少秒以内的视频or音频也可适用 int
                .recordVideoSecond(60)//视频秒数录制 默认60s int
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(request_code);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode == 1){
            if(data!=null){
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                Intent intent = new Intent(this, ImageEditorActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("template_num",TEMPLATE_NUM);
                bundle.putParcelableArrayList("imageList",(ArrayList<? extends Parcelable>) selectList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }else if(resultCode==RESULT_OK&&requestCode == 2){
            if(data!=null){
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                Intent intent = new Intent(this, MediaEditeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("mediaList",(ArrayList<? extends Parcelable>) selectList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }else if(resultCode==RESULT_OK&&requestCode == 3){
            if(data!=null){
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                Intent intent = new Intent(this, PostVideosActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("uploadinfo",(ArrayList<? extends Parcelable>) selectList);
                bundle.putString("selectType","multipleImage");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }

}
