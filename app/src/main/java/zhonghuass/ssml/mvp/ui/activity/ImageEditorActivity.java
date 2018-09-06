package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.chrisbanes.photoview.OnOutsidePhotoTapListener;
import com.github.chrisbanes.photoview.OnSingleFlingListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerImageEditorComponent;
import zhonghuass.ssml.di.module.ImageEditorModule;
import zhonghuass.ssml.mvp.contract.ImageEditorContract;
import zhonghuass.ssml.mvp.presenter.ImageEditorPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.utils.MatrixImageView;
import zhonghuass.ssml.utils.ZoomImageView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ImageEditorActivity extends MBaseActivity<ImageEditorPresenter> implements ImageEditorContract.View{

    @BindView(R.id.edit_img)
    PhotoView editImg;
    @BindView(R.id.out_bg)
    RelativeLayout outBg;
    @BindView(R.id.moban_item)
    LinearLayout mobanItem;
    @BindView(R.id.back_item)
    LinearLayout backItem;
    @BindView(R.id.name_item)
    LinearLayout nameItem;
    @BindView(R.id.mark_item)
    LinearLayout markItem;
    private List<LocalMedia> selectList;
    private Matrix matrix;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerImageEditorComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .imageEditorModule(new ImageEditorModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_image_editor; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = this.getIntent();
        selectList = intent.getParcelableArrayListExtra("selectList");
        matrix = new Matrix();
        //  initImageListener();
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



    @OnClick({R.id.edit_img, R.id.moban_item, R.id.back_item, R.id.name_item, R.id.mark_item})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edit_img:
                break;
            case R.id.moban_item:
                editImg.setRotationBy(30.0f);
                break;
            case R.id.back_item:
                RectF displayRect = editImg.getDisplayRect();
                System.out.println("sssss"+displayRect.bottom);
                break;
            case R.id.name_item:
                Matrix imageMatrix = editImg.getImageMatrix();

                System.out.println("图片信息"+imageMatrix.toString());

                break;
            case R.id.mark_item:
                break;
        }
    }

}
