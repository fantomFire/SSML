package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.OnOutsidePhotoTapListener;
import com.github.chrisbanes.photoview.OnSingleFlingListener;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import zhonghuass.ssml.utils.image.PhotoView;

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
    private Uri uri;
    private String filepath;

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
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("下一步");
        Intent intent = this.getIntent();
        selectList = intent.getParcelableArrayListExtra("imageList");
        int template_num = intent.getIntExtra("template_num", 0);
        System.out.println("模板样式"+template_num);
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



    @OnClick({R.id.edit_img, R.id.moban_item, R.id.back_item, R.id.name_item, R.id.mark_item,R.id.tv_right})
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
                ArmsUtils.startActivity(MediaEditeActivity.class);
                break;
            case R.id.mark_item:
               /* Glide.with(this)
                        .load(filepath)
                        .into(editImg);*/
               ArmsUtils.startActivity(MediaPlayerActivity.class);
                break;
            case R.id.tv_right:
                saveImage();
                break;
        }
    }

    private void saveImage() {
        View view = getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        // 获取状态栏高度
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        System.out.println(statusBarHeight);
        // 获取屏幕长和高
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay()
                .getHeight();
        // 去掉标题栏 //Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455)
        // ;
        System.out.println("width" + width + "    " + (height
                - statusBarHeight - 300));
        Bitmap b = Bitmap.createBitmap(b1, 0, 300, width, 1300   );


        String SavePath = getSDCardPath() + "/ScreenImage";
        // 3.保存Bitmap
        try {
            File path = new File(SavePath);
            // 文件
            filepath = SavePath +"/"+System.currentTimeMillis()+ "edit.png";
            System.out.println("filepath"+filepath);
            File file = new File(filepath);
            if (!path.exists()) {
                path.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
                Toast.makeText(this, "截屏文件已保存至SDCard/AndyDemo/ScreenImage/下",
                        Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取SDCard的目录路径功能
     */
    private String getSDCardPath() {
        File sdcardDir = null;
        // 判断SDCard是否存在
        boolean sdcardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        if (sdcardExist) {
            sdcardDir = Environment.getExternalStorageDirectory();
        }
        return sdcardDir.toString();
    }

}
