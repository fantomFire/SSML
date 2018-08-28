package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
import zhonghuass.ssml.utils.ZoomImageView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ImageEditorActivity extends BaseActivity<ImageEditorPresenter> implements ImageEditorContract.View {

    @BindView(R.id.edit_img)
    ZoomImageView editImg;
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
      //  initImageListener();
    }

    private void initImageListener() {


            editImg.setOnTouchListener(new View.OnTouchListener() {
                private int rawY;
                private int rawX;
                private int lasty;
                private int lastx;
                int startx;//手指第一次点击屏幕时的位置x
                int starty;//手指第一次点击屏幕时的位置y
                private int mx, my;
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            boolean once = editImg.getstate();
                            System.out.println("state"+once);
                            startx = (int) event.getRawX();
                            starty = (int) event.getRawY();
                            rawX = (int) event.getRawX();
                            rawY = (int) event.getRawY();
                            break;
                        case MotionEvent.ACTION_UP:
                            lastx = editImg.getLeft();
                            System.out.println("lastx:"+ lastx);
                            lasty = editImg.getTop();
                            System.out.println("lasty:"+ lasty);
                            break;
                        case MotionEvent.ACTION_MOVE:
                          /*  mx = (int)(event.getRawX() - startx);
                            my = (int)(event.getRawY() - starty);
                            v.layout(mx, my, mx + v.getWidth(), my + v.getHeight());*/

                            int dx = rawX -startx;
                            int dy = rawY -starty;
                            editImg.layout(editImg.getLeft()+dx, editImg.getTop()+dy, editImg.getRight()+dx, editImg.getBottom()+dy);
                           // startx = (int) event.getX();
                           // starty = (int) event.getY();
                            editImg.invalidate();
                            break;
                    }
                    return true;

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



    @OnClick({R.id.edit_img, R.id.moban_item, R.id.back_item, R.id.name_item, R.id.mark_item})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edit_img:
                break;
            case R.id.moban_item:
                break;
            case R.id.back_item:
                break;
            case R.id.name_item:
                break;
            case R.id.mark_item:
                break;
        }
    }
}
