package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.OnClick;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;

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
    @BindView(R.id.rl_pic)
    RelativeLayout rlImage;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.iv_pic)
    SubsamplingScaleImageView ivPic;
    @BindView(R.id.iv_pic2)
    SubsamplingScaleImageView ivPic2;
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


    private int iv1Left, iv1Right, iv1Top, iv1Bottom, iv2Left, iv2Right, iv2Top, iv2Bottom;
    private boolean isChange1;

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolBar("发布", true, "下一步");

        iv1Left = ivPic.getLeft();
        iv1Top = ivPic.getTop();
        iv1Right = iv1Left + 300;
        iv1Bottom = iv1Top + 300;
        iv2Left = ivPic2.getLeft();
        iv2Right = ivPic2.getRight();
        iv2Top = ivPic2.getTop();
        iv2Bottom = ivPic2.getBottom();

        rlImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rlImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int imageWidth = rlImage.getWidth();
                int imageHeight = rlImage.getHeight();
                Log.e("--", "" + imageWidth + "*" + imageHeight);
            }
        });
        ivPic.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ivPic.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int imageWidth = ivPic.getWidth();
                int imageHeight = ivPic.getHeight();
                int[] location = new int[2];
                ivPic.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];
                Log.e("--", "Screenx--->" + x + "  " + "Screeny--->" + y);
                ivPic.getLocationInWindow(location);
                x = location[0];
                y = location[1];
                Log.e("--", "Window--->" + x + "  " + "Window--->" + y);

                Log.e("--", "" + imageWidth + "*" + imageHeight);
            }
        });

//        ivPic.setOnTouchListener(this);
//        ivPic2.setOnTouchListener(this);

        ivPic.setOnTouchListener(new View.OnTouchListener() {
            int startx;//手指第一次点击屏幕时的位置x
            int starty;//手指第一次点击屏幕时的位置y
            int lastx;//手指在屏幕上抬起时的位置x
            int lasty;//手指在屏幕上抬起时的位置y
            int dx, dy, sx, sy;


            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startx = x;
                        starty = y;
                        sx = ivPic.getLeft();
                        sy = ivPic.getTop();
                        break;
                    case MotionEvent.ACTION_UP:
                        lastx = ivPic.getLeft();
                        lasty = ivPic.getTop();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        dx = x - startx;
                        dy = y - starty;
                        startx = (int) event.getRawX();
                        starty = (int) event.getRawY();
                        if (ivPic.getRight() + dx > 525) {
                            if (!isChange1) {
                                ivPic2.setBackground(getResources().getDrawable(R.mipmap.p111));
                                ivPic.setBackground(getResources().getDrawable(R.mipmap.p112));
                                isChange1 = !isChange1;
                            } else {
                                ivPic.setBackground(getResources().getDrawable(R.mipmap.p111));
                                ivPic2.setBackground(getResources().getDrawable(R.mipmap.p112));
                                isChange1 = !isChange1;

                            }
                            return false;
                        }
                        if (ivPic.getLeft() + dx < 0 || ivPic.getTop() + dy < (1350 - 650) || ivPic.getBottom() + dy > 1350) {
                            return false;
                        }

                        ivPic.layout(ivPic.getLeft() + dx, ivPic.getTop() + dy, ivPic.getRight() + dx, ivPic.getBottom() + dy);
                        ivPic.invalidate();
                        break;
                }
                return true;
            }
        });
//        ivPic2.setOnTouchListener(new View.OnTouchListener() {
//            int startx;//手指第一次点击屏幕时的位置x
//            int starty;//手指第一次点击屏幕时的位置y
//            int lastx;//手指在屏幕上抬起时的位置x
//            int lasty;//手指在屏幕上抬起时的位置y
//            int dx, dy;
//
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        startx = (int) event.getRawX();
//                        starty = (int) event.getRawY();
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        lastx = ivPic2.getLeft();
//                        lasty = ivPic2.getTop();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        int x = (int) event.getRawX();
//                        int y = (int) event.getRawY();
//                        dx = x - startx;
//                        dy = y - starty;
//
//                        ivPic2.layout(ivPic2.getLeft() + dx, ivPic2.getTop() + dy, ivPic2.getRight() + dx, ivPic2.getBottom() + dy);
//                        startx = (int) event.getRawX();
//                        starty = (int) event.getRawY();
//                        ivPic2.invalidate();
//                        break;
//                }
//                return true;
//            }
//        });


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


//                ivPic.setImageBitmap(bitmap);
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


    int lastX, lastY, dx, dy;
    boolean ivPic_resetLeftFlag = false;
    boolean ivPic_resetRightFlag = false;
    boolean ivPic2_resetLeftflag = false;
    boolean ivPic2_resetRightflag = false;
    int rightMove_flag;
    int leftMove_flag;

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        int action = event.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                lastX = (int) event.getRawX();
//                lastY = (int) event.getRawY();
//                break;
//            /**
//             * layout(l,t,r,b)
//             * l  Left position, relative to parent
//             t  Top position, relative to parent
//             r  Right position, relative to parent
//             b  Bottom position, relative to parent
//             * */
//            case MotionEvent.ACTION_MOVE:
//                dx = (int) event.getRawX() - lastX;
//                dy = (int) event.getRawY() - lastY;
//
//                int left = v.getLeft() + dx;
//                int top = v.getTop() + dy;
//                int right = v.getRight() + dx;
//                int bottom = v.getBottom() + dy;
//                if (left < 0) {
//                    left = 0;
//                    right = left + v.getWidth();
//                }
//                if (right > 350) {
//                    right = 350;
//                    left = right - v.getWidth();
//                }
//                if (top < 0) {
//                    top = 0;
//                    bottom = top + v.getHeight();
//                }
//                if (bottom > 450) {
//                    bottom = 450;
//                    top = bottom - v.getHeight();
//                }
//                v.layout(left, top, right, bottom);
//                lastX = (int) event.getRawX();
//                lastY = (int) event.getRawY();
//                switch (v.getId()) {
//                    case R.id.iv_pic:
//                        if (dx > 0) {
//                            if (ivPic.getLeft() >= 150) {
//                                if (300 == ivPic2.getLeft()) {
//                                    ivPic2.layout(0, 0, 300, 300);
//                                    Animation translateAnimation = new TranslateAnimation(300, 0, 0, 0);
//                                    translateAnimation.setFillAfter(true);
//                                    translateAnimation.setDuration(600);
//                                    ivPic2.startAnimation(translateAnimation);
//                                    rightMove_flag = 1;
//                                }
//                            }
//                        } else {
//                            if (ivPic.getLeft() <= 150) {
//                                if (0 == ivPic2.getLeft()) {
//                                    ivPic2.layout(300, 0, 600, 300);
//                                    Animation translateAnimation = new TranslateAnimation(-300, 0, 0, 0);
//                                    translateAnimation.setFillAfter(true);
//                                    translateAnimation.setDuration(600);
//                                    ivPic2.startAnimation(translateAnimation);
//                                    leftMove_flag = 1;
//                                }
//                            }
//                        }
//                        break;
//                    case R.id.iv_pic2:
//                        if (dx > 0) {
//                            if (ivPic2.getLeft() >= 150) {
//                                if (300 == ivPic.getLeft()) {
//                                    ivPic.layout(0, 0, 300, 300);
//                                    Animation translateAnimation = new TranslateAnimation(300, 0, 0, 0);
//                                    translateAnimation.setFillAfter(true);
//                                    translateAnimation.setDuration(600);
//                                    ivPic.startAnimation(translateAnimation);
//                                    rightMove_flag = 1;
//                                }
//                            }
//                        } else {
//                            if (ivPic2.getLeft() <= 150) {
//                                if (0 == ivPic.getLeft()) {
//                                    ivPic.layout(300, 0, 600, 300);
//                                    Animation translateAnimation = new TranslateAnimation(-300, 0, 0, 0);
//                                    translateAnimation.setFillAfter(true);
//                                    translateAnimation.setDuration(600);
//                                    ivPic.startAnimation(translateAnimation);
//                                    leftMove_flag = 1;
//                                }
//                            }
//                        }
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//
//                switch (v.getId()) {
//                    case R.id.iv_pic:
//                        if (dx > 0) {
//                            if (ivPic.getLeft() <= 150) {
//                                ivPic_resetLeftFlag = true;
//                            }
//                            if (ivPic.getLeft() >= 300) {
//                                ivPic_resetRightFlag = true;
//                            }
//                        } else {
//                            if (ivPic.getLeft() >= 150) {
//                                ivPic_resetRightFlag = true;
//                            }
//
//                        }
//                        if (1 == rightMove_flag) {
//                            rightMove_flag = 0;
//                            int x1 = ivPic.getLeft();
//                            int x2 = ivPic.getTop();
//                            ivPic.layout(300, 0, 600, 300);
//                            Animation translateAnimation = new TranslateAnimation(x1 - 300, 0, x2, 0);
//                            translateAnimation.setFillAfter(true);
//                            translateAnimation.setDuration(600);
//                            ivPic.startAnimation(translateAnimation);
//                            break;
//                        }
//                        if (ivPic_resetLeftFlag) {
//                            ivPic_resetLeftFlag = false;
//                            int x1 = ivPic.getLeft();
//                            int high = ivPic.getTop();
//                            ivPic.layout(0, 0, 300, 300);
//                            Animation translateAnimation = new TranslateAnimation(x1, 0, high, 0);
//                            translateAnimation.setFillAfter(true);
//                            translateAnimation.setDuration(600);
//                            ivPic.startAnimation(translateAnimation);
//                            break;
//                        }
//                        if (1 == leftMove_flag) {
//                            leftMove_flag = 0;
//                            int x1 = ivPic.getLeft();
//                            int x2 = ivPic.getTop();
//                            ivPic.layout(0, 0, 300, 300);
//                            Animation translateAnimation = new TranslateAnimation(x1, 0, x2, 0);
//                            translateAnimation.setFillAfter(true);
//                            translateAnimation.setDuration(600);
//                            ivPic.startAnimation(translateAnimation);
//                            break;
//                        }
//                        if (ivPic_resetRightFlag) {
//                            ivPic_resetRightFlag = false;
//                            int x1 = ivPic.getLeft();
//                            int x2 = ivPic.getTop();
//                            ivPic.layout(300, 0, 600, 300);
//                            Animation translateAnimation = new TranslateAnimation(x1 - 300, 0, x2, 0);
//                            translateAnimation.setFillAfter(true);
//                            translateAnimation.setDuration(600);
//                            ivPic.startAnimation(translateAnimation);
//                            break;
//                        }
//
//                        break;
//                    case R.id.iv_pic2:
//                        if (dx > 0) {
//                            if (ivPic2.getLeft() <= 150) {
//                                ivPic2_resetLeftflag = true;
//                            }
//                            if (ivPic2.getLeft() >= 300) {
//                                ivPic2_resetRightflag = true;
//                            }
//                        } else {
//                            if (ivPic2.getLeft() >= 150) {
//                                ivPic2_resetRightflag = true;
//                            }
//                        }
//                        if (1 == rightMove_flag) {
//                            rightMove_flag = 0;
//                            int x1 = ivPic2.getLeft();
//                            int x2 = ivPic2.getTop();
//                            ivPic2.layout(300, 0, 600, 300);
//                            Animation translateAnimation = new TranslateAnimation(x1 - 300, 0, x2, 0);
//                            translateAnimation.setFillAfter(true);
//                            translateAnimation.setDuration(600);
//                            ivPic2.startAnimation(translateAnimation);
//                            break;
//                        }
//                        if (ivPic2_resetLeftflag) {
//                            ivPic2_resetLeftflag = false;
//                            int x1 = ivPic2.getLeft();
//                            int high = ivPic2.getTop();
//                            ivPic2.layout(0, 0, 300, 300);
//                            Animation translateAnimation = new TranslateAnimation(x1, 0, high, 0);
//                            translateAnimation.setFillAfter(true);
//                            translateAnimation.setDuration(600);
//                            ivPic2.startAnimation(translateAnimation);
//                            break;
//                        }
//                        if (1 == leftMove_flag) {
//                            leftMove_flag = 0;
//                            int x1 = ivPic2.getLeft();
//                            int x2 = ivPic2.getTop();
//                            ivPic2.layout(0, 0, 300, 300);
//                            Animation translateAnimation = new TranslateAnimation(x1, 0, x2, 0);
//                            translateAnimation.setFillAfter(true);
//                            translateAnimation.setDuration(600);
//                            ivPic2.startAnimation(translateAnimation);
//                            break;
//                        }
//                        if (ivPic2_resetRightflag) {
//                            ivPic2_resetRightflag = false;
//                            int x1 = ivPic2.getLeft();
//                            int x2 = ivPic2.getTop();
//                            ivPic2.layout(300, 0, 600, 300);
//                            Animation translateAnimation = new TranslateAnimation(x1 - 300, 0, x2, 0);
//                            translateAnimation.setFillAfter(true);
//                            translateAnimation.setDuration(600);
//                            ivPic2.startAnimation(translateAnimation);
//                            break;
//                        }
//                        break;
//                }
//                break;
//        }
//
//        return false;
//    }


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
                ivMb.setBackground(getResources().getDrawable(R.mipmap.meinv));
                break;
        }
    }
}
