package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.github.library.baseAdapter.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerPostVideosComponent;
import zhonghuass.ssml.di.module.PostVideosModule;
import zhonghuass.ssml.mvp.contract.PostVideosContract;
import zhonghuass.ssml.mvp.model.appbean.RecomDetailBean;
import zhonghuass.ssml.mvp.presenter.PostVideosPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.mvp.ui.adapter.ImagesAdapter;
import zhonghuass.ssml.mvp.ui.adapter.PostVideoAdapter;
import zhonghuass.ssml.utils.Constants;
import zhonghuass.ssml.utils.PrefUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class PostVideosActivity extends MBaseActivity<PostVideosPresenter> implements PostVideosContract.View, AMapLocationListener {

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
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private List<RecomDetailBean> list = new ArrayList<>();
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
    private ArrayList<LocalMedia> imagesUpList = new ArrayList<>();
    private Bitmap bitmap;
    private String bitmapPath;
    private LocalMedia localMediaLast;
    private String urlPath;
    private String userId;
    private File fileDir;
    private File currentFile;
    private boolean isRuning;
    private String theme_id = "0";
    private String member_type;
    private String oneImagePath;
    private String mediaLength;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    private String localtion = "西安";

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
        llBack.setOnClickListener(v -> finish());
        intent = getIntent();
        userId = PrefUtils.getString(this, Constants.USER_ID, "");
        member_type = PrefUtils.getString(this, Constants.MEMBER_TYPE, "");

        selectType = intent.getStringExtra("selectType");

        if (selectType.equals("editMedia")) {

            imageUp.setVisibility(View.VISIBLE);
            imgRec.setVisibility(View.GONE);

            mediaPath = intent.getStringExtra("mediaPath");
            mediaLength = intent.getStringExtra("mediaLength");
            System.out.println("mediaLength"+mediaLength);
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

        } else if (selectType.equals("oneImage")) {
            oneImagePath = intent.getStringExtra("imagePath");
            Glide.with(this)
                    .load(oneImagePath)
                    .into(imageUp);
        }


        //初始化每日一语
        initDialySay();

        mPresenter.getInviteData();
        getLocal();

    }

    private void getLocal() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();


    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                amapLocation.getCountry();//国家信息
                String provence = amapLocation.getProvince();//省信息
                String city = amapLocation.getCity();//城市信息
                String area = amapLocation.getDistrict();//城区信息
                String street = amapLocation.getStreet();//街道信息
                amapLocation.getStreetNum();//街道门牌号信息
                amapLocation.getCityCode();//城市编码
                amapLocation.getAdCode();//地区编码
                System.out.println("省信息" + provence + city + area + street);
                localtion = city + area + street;
                tvEara.setText(localtion);
                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                    /*//设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(amapLocation);
                    //添加图钉
                    aMap.addMarker(getMarkerOptions(amapLocation));*/
                    //获取定位信息
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity() + "" + amapLocation.getProvince() + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
                    // Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();
                    isFirstLoc = false;
                }


            } else {
                Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_LONG).show();
            }
        }


    }


    private void initImages() {


        imageUp.setVisibility(View.GONE);
        imgRec.setVisibility(View.VISIBLE);
        imagesList = intent.getParcelableArrayListExtra("uploadinfo");
        urlPath = Uri.parse(ANDROID_RESOURCE + getPackageName() + FOREWARD_SLASH + R.mipmap.fb_icon_12).toString();
        localMediaLast = new LocalMedia();
        localMediaLast.setPath(urlPath);
        imagesList.add(localMediaLast);

        imgRec.setLayoutManager(new GridLayoutManager(this, 3));
        imagesAdapter = new ImagesAdapter(R.layout.image_item, imagesList);
        imgRec.setAdapter(imagesAdapter);
        imagesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (imagesList.size() < 10) {
                    if (position == imagesList.size() - 1) {
                        selectImages(10 - imagesList.size());

                    }
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            if (data != null) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                imagesList.addAll(imagesList.size() - 1, selectList);
                imagesAdapter.notifyDataSetChanged();
            }
        }


    }

    private void initDialySay() {
        rvGrid.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new PostVideoAdapter(R.layout.postvideos_item, list);
        rvGrid.setAdapter(adapter);
        rvGrid.setNestedScrollingEnabled(false);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                tvTag.setText(list.get(position).getTheme_title());
                theme_id = list.get(position).getTheme_id();
            }
        });
    }

    private void setImage(String mediaPath) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(mediaPath);
        bitmap = mediaMetadataRetriever.getFrameAtTime();
        imageUp.setImageBitmap(bitmap);
        File sdDir = Environment.getExternalStorageDirectory();
        fileDir = new File(sdDir.getPath() + "/IMAGE");
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        saveFile(bitmap);

    }

    public void saveFile(Bitmap bitmap) {

        SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = time.format(System.currentTimeMillis());
        currentFile = new File(fileDir, fileName + ".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(currentFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showLoading() {
        isRuning = true;
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        isRuning = false;
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
    public void showdata(List<RecomDetailBean> recomList) {
        list = recomList;
        adapter.setNewData(recomList);
    }

    @Override
    public void closeActivity() {
        if (PublishActivity.publishActivity != null) {
            PublishActivity.publishActivity.finish();
        }
        if (ImageEditorActivity.imageEditorActivity != null) {
            ImageEditorActivity.imageEditorActivity.finish();
        }
        if (MediaEditeActivity.mediaEditeActivity != null) {
            MediaEditeActivity.mediaEditeActivity.finish();
        }
        finish();

    }


    @OnClick({R.id.eara_next, R.id.dialy_next, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.eara_next:
                break;
            case R.id.dialy_next:
                break;
            case R.id.tv_right:

                if (selectType.equals("editMedia")) {
                    uploadMedia();
                } else if (selectType.equals("multipleImage")) {
                    checkData();
                } else if (selectType.equals("oneImage")) {
                    upLoadOneImage();
                }

                break;
        }
    }

    private void upLoadOneImage() {
        String mContent = etContent.getText().toString();

        imagesUpList.clear();
        LocalMedia localMedia = new LocalMedia();
        localMedia.setPath(oneImagePath);
        imagesUpList.add(localMedia);

        if (isRuning) {
            Toast.makeText(this, "文件上传中...", Toast.LENGTH_SHORT).show();
            return;
        }
        showLoading();
        mPresenter.upImages(imagesUpList, mContent, theme_id, userId, member_type, localtion);

    }

    private void uploadMedia() {
        if (isRuning) {
            Toast.makeText(this, "视频上传中...", Toast.LENGTH_SHORT).show();
            return;
        }
        String mContent = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(mContent)) {
            Toast.makeText(this, "说点什么吧!", Toast.LENGTH_SHORT).show();
            return;
        }
        System.out.println("mtext.length()"+mContent.length());
        if(mContent.length()<10||mContent.length()>60){
            Toast.makeText(this, "内容标题应为5~30个字符!", Toast.LENGTH_SHORT).show();
            return;
        }
        showLoading();
        mPresenter.upLoadData(mediaPath, mContent, theme_id, userId, member_type, currentFile.getPath(), mediaLength, localtion);
    }

    private void checkData() {
        String mContent = etContent.getText().toString();

        imagesUpList.clear();
        imagesUpList.addAll(imagesList);
        //   mPresenter.upLoadData(mList, mContent, userEare, dailyTag, );
        String mtext = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(mtext)) {
            Toast.makeText(this, "说点什么吧!", Toast.LENGTH_SHORT).show();
            return;
        }

        String path = imagesUpList.get(imagesList.size() - 1).getPath();
        if (path.equals(urlPath)) {
            imagesUpList.remove(imagesList.size() - 1);
        }
        if (isRuning) {
            Toast.makeText(this, "文件上传中...", Toast.LENGTH_SHORT).show();
            return;
        }
        showLoading();
        mPresenter.upImages(imagesUpList, mContent, theme_id, userId, member_type, localtion);

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


}
