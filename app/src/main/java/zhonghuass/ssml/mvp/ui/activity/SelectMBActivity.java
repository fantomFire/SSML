package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;

import com.github.library.baseAdapter.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import zhonghuass.ssml.di.component.DaggerSelectMBComponent;
import zhonghuass.ssml.di.module.SelectMBModule;
import zhonghuass.ssml.mvp.contract.SelectMBContract;
import zhonghuass.ssml.mvp.presenter.SelectMBPresenter;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.mvp.ui.adapter.TemplateAdapter;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class SelectMBActivity extends MBaseActivity<SelectMBPresenter> implements SelectMBContract.View {

    @BindView(R.id.rvMb)
    RecyclerView rvMb;
    private List<String> mList = new ArrayList<>();
    private int TEMPLATE_NUM=1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSelectMBComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .selectMBModule(new SelectMBModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_select_mb; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mList.add("http://video.zhonghuass.cn/public/uploadfile/templet/1.jpg");
        mList.add("http://video.zhonghuass.cn/public/uploadfile/templet/2.jpg");
        mList.add("http://video.zhonghuass.cn/public/uploadfile/templet/3.jpg");
        mList.add("http://video.zhonghuass.cn/public/uploadfile/templet/4.jpg");
        tvTitle.setText("模板选择");
        rvMb.setLayoutManager(new GridLayoutManager(this, 2));
        TemplateAdapter templateAdapter = new TemplateAdapter(R.layout.imgselect, mList);
        rvMb.setAdapter(templateAdapter);
        templateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                System.out.println("posiont"+position);
                TEMPLATE_NUM = position+1;
                selectPhoto(6);
            }
        });
    }

    private void selectPhoto(int request_code) {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(5)// 最大图片选择数量 int
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

        if (resultCode == RESULT_OK && requestCode == 6) {
            if (data != null) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                Intent intent = new Intent(this, ImageEditorActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("template_num", TEMPLATE_NUM);
                bundle.putParcelableArrayList("imageList", (ArrayList<? extends Parcelable>) selectList);
                intent.putExtras(bundle);
                startActivity(intent);
            }


        }
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
