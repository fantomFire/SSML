package zhonghuass.ssml.mvp.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.bumptech.glide.Glide;
import com.github.library.pickerView.scrollPicker.CustomCityPicker;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.OnClick;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerMyInfoComponent;
import zhonghuass.ssml.di.module.MyInfoModule;
import zhonghuass.ssml.mvp.contract.MyInfoContract;
import zhonghuass.ssml.mvp.model.appbean.UserInfoBean;
import zhonghuass.ssml.mvp.presenter.MyInfoPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.utils.ACache;
import zhonghuass.ssml.utils.CircleImageView;
import zhonghuass.ssml.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MyInfoActivity extends MBaseActivity<MyInfoPresenter> implements MyInfoContract.View {

    @BindView(R.id.civ_photo)
    CircleImageView civPhoto;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.et_about_my)
    EditText etMy;
    @BindView(R.id.fl_progress)
    FrameLayout flProgress;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private CustomCityPicker cityPicker;
    private Dialog cityPickerDialog;
    private List<LocalMedia> selectList = new ArrayList<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myInfoModule(new MyInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolBar("编辑资料", true, "保存");

        UserInfoBean mUserInfo = (UserInfoBean) ACache.get(this).getAsObject(Constants.USERINFO);
        if (mUserInfo != null) {
            Glide.with(this).load(mUserInfo.avatar).into(civPhoto);
            etName.setText(mUserInfo.nickname);
            etMy.setText(mUserInfo.introduction);
            tvArea.setText(mUserInfo.provincie + "-" + mUserInfo.city + "-" + mUserInfo.area);
        }


        cityPicker = new CustomCityPicker(this, new CustomCityPicker.ResultHandler() {
            @Override
            public void handle(String result) {
                tvArea.setText(result);
            }

            @Override
            public void sendId(String mProvinceId, String mCityId, String mAreaId) {
                provincie = mProvinceId;
                city = mCityId;
                area = mAreaId;
            }
        });
        //提前初始化数据，这样可以加载快一些。
        cityPicker.initJson(null);

        etMy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("--", "输入：" + s);
                tvNum.setText(s.length() + "/140");
            }
        });
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


    private String provincie = "", city = "", area = "";

    @OnClick({R.id.civ_photo, R.id.ll_area, R.id.tv_right})
    public void onViewClicked(View view) {
        super.onViewClicked(view);
        switch (view.getId()) {
            case R.id.civ_photo:
                selectPhoto(PictureMimeType.ofImage(), 1);
                break;
            case R.id.ll_area:
                cityPicker.show();
                break;
            case R.id.tv_right:
                //进度条
                flProgress.setVisibility(View.VISIBLE);
                //提交保存
                mPresenter.updateInfo(selectList, "1", etName.getText().toString(), provincie, city, area, etMy.getText().toString());
                break;
        }
    }

    private void selectPhoto(int type, int request_code) {
        PictureSelector.create(this)
                .openGallery(type)//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量 int
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            if (data != null) {
                selectList = PictureSelector.obtainMultipleResult(data);
                Glide.with(this)
                        .load(selectList.get(0).getPath())
                        .into(civPhoto);
            }
        }
    }

    @Override
    public void showUpdateSuccess(List<UserInfoBean> userInfoBeans) {
        flProgress.setVisibility(View.GONE);

        Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
        //存起来
        ACache.get(this).put(Constants.USERINFO, userInfoBeans.get(0));
        //finish
        finish();
    }

    @Override
    public void showError() {
        flProgress.setVisibility(View.GONE);
    }
}
