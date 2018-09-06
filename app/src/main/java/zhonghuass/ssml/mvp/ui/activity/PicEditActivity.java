package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.OnClick;


import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerPicEditComponent;
import zhonghuass.ssml.di.module.PicEditModule;
import zhonghuass.ssml.mvp.contract.PicEditContract;
import zhonghuass.ssml.mvp.presenter.PicEditPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class PicEditActivity extends MBaseActivity<PicEditPresenter> implements PicEditContract.View {

    @BindView(R.id.fl_image)
    FrameLayout flImage;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.iv_mb)
    ImageView ivMb;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPicEditComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .picEditModule(new PicEditModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_pic_edit; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolBar("发布", true, "下一步");
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ivPic.setDrawingCacheEnabled(true);
                Bitmap bm1 = Bitmap.createBitmap(ivPic.getDrawingCache());
                ivPic.setDrawingCacheEnabled(false);

                ivBg.setDrawingCacheEnabled(true);
                Bitmap bm2 = Bitmap.createBitmap(ivBg.getDrawingCache());
                ivBg.setDrawingCacheEnabled(false);

//                Bitmap bm2 = ((BitmapDrawable) ivBg.getDrawable()).getBitmap();
                Bitmap bm3 = mergeBitmap(bm2, bm1);

                flImage.setDrawingCacheEnabled(true);

                flImage.measure(

                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),

                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

                flImage.layout(0, 0, flImage.getMeasuredWidth(),

                        flImage.getMeasuredHeight());


                flImage.buildDrawingCache();


                Bitmap bitmap = flImage.getDrawingCache();


                ivPic.setImageBitmap(bitmap);
            }
        });
    }

    private Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap) {
        Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth(), firstBitmap.getHeight(), firstBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        canvas.drawBitmap(secondBitmap, 0, 0, null);
        return bitmap;
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

    @OnClick({R.id.tv_1, R.id.tv_2})
    public void onViewClicked(View view) {
        super.onViewClicked(view);
        switch (view.getId()) {
            case R.id.tv_2:
                ivBg.setBackgroundColor(getResources().getColor(R.color.colorcf1313));
                break;
            case R.id.tv_1:
                ivMb.setImageResource(R.mipmap.muban);
                break;
        }
    }
}
