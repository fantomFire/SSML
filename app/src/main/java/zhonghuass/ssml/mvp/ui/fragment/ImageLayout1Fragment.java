package zhonghuass.ssml.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.*;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.OnSingleFlingListener;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.entity.LocalMedia;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerImageLayout1Component;
import zhonghuass.ssml.di.module.ImageLayout1Module;
import zhonghuass.ssml.mvp.ToActivityMsg;
import zhonghuass.ssml.mvp.ToFragmentMsg;
import zhonghuass.ssml.mvp.contract.ImageLayout1Contract;
import zhonghuass.ssml.mvp.presenter.ImageLayout1Presenter;
import zhonghuass.ssml.mvp.ui.activity.ImageEditorActivity;
import zhonghuass.ssml.utils.CircleImageView;
import zhonghuass.ssml.utils.CustomRadioGroup;
import zhonghuass.ssml.utils.EventBusUtils;
import zhonghuass.ssml.utils.ImageUtils;
import zhonghuass.ssml.utils.image.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ImageLayout1Fragment extends BaseFragment<ImageLayout1Presenter> implements ImageLayout1Contract.View, OnSingleFlingListener, ImageEditorActivity.IOnFocusListenable {

    @BindView(R.id.stickerView)
    public RelativeLayout stickerView;
    @BindView(R.id.rl_bg)
    public RelativeLayout rlBg;
    @BindView(R.id.rl_mb)
    public RelativeLayout rlMb;
    @BindView(R.id.tv1)
    public TextView tv1;

    @BindView(R.id.iv1)
    public PhotoView image1;
    @BindView(R.id.iv2)
    public PhotoView image2;


    private ToActivityMsg eventMsg;
    private boolean isClick;
    private boolean isRotate;
    int defaultHeight, defaultWidth;// 屏幕宽高
    private List<String> pathList = new ArrayList();
    private View mDrawImage;//最终保存图片区域
    private int statusBarHeight, titleBarHeight;
    private static List<LocalMedia> selectList;
    private PopupWindow imageMenuPop;

    public static ImageLayout1Fragment newInstance() {
        ImageLayout1Fragment fragment = new ImageLayout1Fragment();
        return fragment;
    }

    public static ImageLayout1Fragment newInstance(List<LocalMedia> imgList) {
        ImageLayout1Fragment fragment = new ImageLayout1Fragment();
        selectList = imgList;
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerImageLayout1Component //如找不到该类,请编译 一下项目
                .builder()
                .appComponent(appComponent)
                .imageLayout1Module(new ImageLayout1Module(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_layout1, container, false);
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initBackPopupWindow();
        path1 = selectList.get(0).getPath();
        path2 = selectList.get(1).getPath();
        pathList.add(path1);
        pathList.add(path2);

        image1.setScaleType(ImageView.ScaleType.CENTER);
        image2.setScaleType(ImageView.ScaleType.CENTER);

        Glide.with(this)
                .load(path1)
                .into(image1);
        Glide.with(this)
                .load(path2)
                .into(image2);

        //把模板中默认的textView添加进去，因为点击之后要弹出底部菜单修改
        textViews.add(tv1);


        //获取屏幕宽高
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        defaultWidth = size.x;
        defaultHeight = size.y;

        // 获取状态栏高度
        Rect frame = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        statusBarHeight = frame.top;


    }

    @Override
    public void setData(@Nullable Object data) {

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

    }

    @OnClick({R.id.rl_mb, R.id.iv1, R.id.iv2, R.id.tv1})
    public void onViewClicked(View view) {
        closeTextViewMenu();
        switch (view.getId()) {
            case R.id.rl_mb:
                break;
            case R.id.iv1:
                Log.e("--", "点击了，弹菜单。");
                clickImageView = image1;
                imageMenuPop.showAsDropDown(image1);
                break;
            case R.id.iv2:
                break;
            case R.id.tv1:
                toShowPop(0, 1);
                break;
        }
    }

    private PhotoView clickImageView;
    private boolean imageMenuPopShow;

    private int rotateNum = 0;

    private void initBackPopupWindow() {
        View contentview = getLayoutInflater().inflate(R.layout.popupwindow_image_menu, null);
        contentview.setFocusable(true); // 这个很重要
        contentview.setFocusableInTouchMode(true);

        imageMenuPop = new PopupWindow(contentview, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        TextView tvChange = contentview.findViewById(R.id.tv_change);
        TextView tvRotate = contentview.findViewById(R.id.tv_rotate);

        tvRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateNum++;
//                Matrix matrix = new Matrix(); //旋转图片 动作
//                matrix.setRotate(90);//旋转角度
//                Bitmap bitmap = ((BitmapDrawable) ((PhotoView) clickImageView).getDrawable()).getBitmap();
////                Bitmap bitmap = ImageUtils.getBitMBitmap(selectList.get(0).getPath());
//                int width = selectList.get(0).getWidth();
//                int height = selectList.get(0).getHeight(); // 创建新的图片
//                Log.e("--", "图片宽高" + width + "-" + height);
//                int width2 = bitmap.getWidth();
//                int height2 = bitmap.getHeight(); // 创建新的图片
//                Log.e("--", "view宽高" + width2 + "-" + height2);
////                Bitmap resizedBitmap;
////                if ((rotateNum ^ 2) == 0) {
////                    resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
////                } else {
////                    resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, height, width, matrix, true);
////                }
//                Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width2, height2, matrix, true);
//                BitmapDrawable bitmapDrawable = new BitmapDrawable(resizedBitmap);
//                clickImageView.setImageDrawable(bitmapDrawable);

                Bitmap bmp = BitmapFactory.decodeFile(selectList.get(0).getPath());
                BitmapDrawable bitmapDrawable = new BitmapDrawable(rotaingImageView(90 * rotateNum, bmp));
                clickImageView.setImageDrawable(bitmapDrawable);
            }
        });


        imageMenuPop.setBackgroundDrawable(new ColorDrawable(0x11111111));
        // 设置好参数之后再show
        imageMenuPop.setOutsideTouchable(true);


    }


    /*
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);


        float nn = bitmap.getWidth() / image1.getWidth();
        int w = (int) (nn * bitmap.getWidth());
        int h = (int) (nn * bitmap.getHeight());

        
        Log.e("--","图宽"+bitmap.getWidth());
        Log.e("--","V宽"+image1.getWidth());


        // 创建新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


    //通知编辑页面弹出底部菜单
    private void toShowPop(int id, int pop) {
        eventMsg = new ToActivityMsg();
        if (pop == 1) {
            eventMsg.showFontPop = true;
            eventMsg.color = textViews.get(id).getCurrentTextColor();
            eventMsg.text = textViews.get(id).getText().toString();
        }
        if (pop == 2) {
            eventMsg.showTagPop = true;
            eventMsg.text = tagTextViews.get(id).getText().toString();
        }
        eventMsg.viewId = id;
        eventMsg.fragment = 1;
        EventBusUtils.post(eventMsg);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusUtils.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.unregister(this);
    }

    private List<TextView> textViews = new ArrayList<>();
    private List<View> views = new ArrayList<>();
    private List<TextView> tagTextViews = new ArrayList<>();
    private List<View> tagViews = new ArrayList<>();

    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showMsg(ToFragmentMsg msg) {
        if (msg.fragment == 1) {
            closeTextViewMenu();
            if (msg.isFontOk) {
                textViews.get(msg.viewId).setText(msg.text);
                textViews.get(msg.viewId).setTextSize(msg.size);
                textViews.get(msg.viewId).setTextColor(getResources().getColor(msg.color));
            }
            if (msg.isAddText) {
                addDelRotateView(msg);
            }
            if (msg.isTagOk) {
                addTagView(msg);
            }
        }
    }

    //点击View外部隐藏删除旋转按钮
    private void closeTextViewMenu() {
        if (views.size() > 0) {
            for (View view : views) {
                ImageView ivDelete = (ImageView) view.findViewById(R.id.iv_delete);
                ImageView ivRotate = (ImageView) view.findViewById(R.id.iv_rotate);
                ivDelete.setVisibility(View.INVISIBLE);
                ivRotate.setVisibility(View.INVISIBLE);
            }
        }
        if (tagViews.size() > 0) {
            for (View view : tagViews) {
                ImageView ivDelete = (ImageView) view.findViewById(R.id.iv_delete);
                ivDelete.setVisibility(View.INVISIBLE);
            }
        }
    }

    //添加一个带删除旋转的view
    private void addDelRotateView(ToFragmentMsg msg) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_add_text, null);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        ImageView ivDelete = (ImageView) view.findViewById(R.id.iv_delete);
        ImageView ivRotate = (ImageView) view.findViewById(R.id.iv_rotate);
        textView.setText(msg.text);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(msg.size);
        textView.setTag(textViews.size());
        view.setPadding(5, 5, 5, 5);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        view.setLayoutParams(lp);
        stickerView.addView(view);
        eventMsg = new ToActivityMsg();
        eventMsg.isViewId = true;
        eventMsg.viewId = textViews.size();
        EventBusUtils.post(eventMsg);
        textViews.add(textView);
        views.add(view);

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stickerView.removeView(view);
                views.remove((int) textView.getTag() - 1);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("--", "点击了" + ((int) textView.getTag()));
                ivDelete.setVisibility(View.VISIBLE);
                ivRotate.setVisibility(View.VISIBLE);
                toShowPop((int) textView.getTag(), 1);
            }
        });
        touchView(view);

    }

    //添加一个tag
    private void addTagView(ToFragmentMsg msg) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_add_tag, null);
        LinearLayout llTag = (LinearLayout) view.findViewById(R.id.ll_tag);
        llTag.setBackgroundColor(getResources().getColor(msg.type));
        TextView textView = (TextView) view.findViewById(R.id.tv_tag);
        ImageView ivDelete = (ImageView) view.findViewById(R.id.iv_delete);
        textView.setText(msg.text);
        textView.setTag(tagTextViews.size());
        view.setPadding(5, 5, 5, 5);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        view.setLayoutParams(lp);
        stickerView.addView(view);
        eventMsg = new ToActivityMsg();
        eventMsg.isViewId = true;
        eventMsg.viewId = tagTextViews.size();
        EventBusUtils.post(eventMsg);
        tagTextViews.add(textView);
        tagViews.add(view);

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stickerView.removeView(view);
                tagViews.remove((int) textView.getTag());
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("--", "点击了" + ((int) textView.getTag()));
                ivDelete.setVisibility(View.VISIBLE);
                toShowPop((int) textView.getTag(), 2);
            }
        });
        touchView(view);

    }

    private long firstTime = 0;
    private long secondTime;

    private void touchView(View view) {
        final ImageView ivRotate = (ImageView) view.findViewById(R.id.iv_rotate);
        view.setOnTouchListener(new View.OnTouchListener() {
            int startx;//手指第一次点击屏幕时的位置x
            int starty;//手指第一次点击屏幕时的位置y
            int lastx;//手指在屏幕上抬起时的位置x
            int lasty;//手指在屏幕上抬起时的位置y
            int dx, dy;
            int oriLeft, oriRight, oriTop, oriBottom;
            float oriRotation = 0;//初始旋转角度

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isClick = false;//当按下的时候设置isclick为false，具体原因看后边的讲解
                        firstTime = System.currentTimeMillis();
                        startx = (int) event.getRawX();
                        starty = (int) event.getRawY();

                        oriLeft = v.getLeft();
                        oriRight = v.getRight();
                        oriTop = v.getTop();
                        oriBottom = v.getBottom();
                        oriRotation = v.getRotation();


                        //判断按下的点是否在旋转按钮
                        isRotate = isTouchPointInView(ivRotate, startx, starty);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!isRotate) {
                            secondTime = System.currentTimeMillis();
                            //当从点击到弹起小于半秒的时候,则判断为点击,如果超过则不响应点击事件
                            if ((secondTime - firstTime) < 0.3 * 1000L) {
                                isClick = false;
                            } else {
                                isClick = true;
                            }
                            lastx = view.getLeft();
                            lasty = view.getTop();

                            // 重新设置位置，否则再次添加时候会位移。
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT);
                            params.leftMargin = lastx;
                            params.topMargin = lasty;
                            view.setLayoutParams(params);
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        isClick = true;//当按钮被移动的时候设置isclick为true
                        if (isRotate && ivRotate.getVisibility() == View.VISIBLE) {
                            // 旋转
                            int x = (int) event.getRawX();
                            int y = (int) event.getRawY();
                            // 中心点
                            Point center = new Point(oriLeft + (oriRight - oriLeft) / 2, oriTop + (oriBottom - oriTop) / 2);
                            // 按住的旋转按钮点
                            Point first = new Point(startx, starty);
                            // 旋转后的点
                            Point second = new Point(x, y);

                            // 旋转角度
                            oriRotation += angle(center, first, second);
                            view.setRotation(oriRotation);

                            startx = (int) event.getRawX();
                            starty = (int) event.getRawY();

                        } else {
                            // 移动
                            int x = (int) event.getRawX();
                            int y = (int) event.getRawY();
                            dx = x - startx;
                            dy = y - starty;
                            if (Math.abs(dx) > 10 || Math.abs(dy) > 10) {
                                view.layout(view.getLeft() + dx, view.getTop() + dy,
                                        view.getRight() + dx, view.getBottom() + dy);
                                startx = (int) event.getRawX();
                                starty = (int) event.getRawY();
                                view.invalidate();
                            }
                        }
                        break;
                }
                return isClick;
            }
        });
    }

    //根据坐标系中的3点确定夹角的方法（注意：夹角是有正负的）
    public float angle(Point cen, Point first, Point second) {
        float dx1, dx2, dy1, dy2;

        dx1 = first.x - cen.x;
        dy1 = first.y - cen.y;
        dx2 = second.x - cen.x;
        dy2 = second.y - cen.y;

        // 计算三边的平方
        float ab2 = (second.x - first.x) * (second.x - first.x) + (second.y - first.y) * (second.y - first.y);
        float oa2 = dx1 * dx1 + dy1 * dy1;
        float ob2 = dx2 * dx2 + dy2 * dy2;

        // 根据两向量的叉乘来判断顺逆时针
        boolean isClockwise = ((first.x - cen.x) * (second.y - cen.y) - (first.y - cen.y) * (second.x - cen.x)) > 0;

        // 根据余弦定理计算旋转角的余弦值
        double cosDegree = (oa2 + ob2 - ab2) / (2 * Math.sqrt(oa2) * Math.sqrt(ob2));

        // 异常处理，因为算出来会有误差绝对值可能会超过一，所以需要处理一下
        if (cosDegree > 1) {
            cosDegree = 1;
        } else if (cosDegree < -1) {
            cosDegree = -1;
        }

        // 计算弧度
        double radian = Math.acos(cosDegree);

        // 计算旋转过的角度，顺时针为正，逆时针为负
        return (float) (isClockwise ? Math.toDegrees(radian) : -Math.toDegrees(radian));

    }

    //(x,y)是否在view的区域内
    private boolean isTouchPointInView(View view, int x, int y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        //view.isClickable() &&
        if (y >= top && y <= bottom && x >= left
                && x <= right) {
            return true;
        }
        return false;
    }

    private void touchTextView(TextView textView) {
        textView.setOnTouchListener(new View.OnTouchListener() {
            int startx;//手指第一次点击屏幕时的位置x
            int starty;//手指第一次点击屏幕时的位置y
            int lastx;//手指在屏幕上抬起时的位置x
            int lasty;//手指在屏幕上抬起时的位置y
            int dx, dy;

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isClick = false;//当按下的时候设置isclick为false，具体原因看后边的讲解
                        firstTime = System.currentTimeMillis();
                        startx = (int) event.getRawX();
                        starty = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        secondTime = System.currentTimeMillis();
                        //当从点击到弹起小于半秒的时候,则判断为点击,如果超过则不响应点击事件
                        if ((secondTime - firstTime) < 0.3 * 1000L) {
                            isClick = false;
                        } else {
                            isClick = true;
                        }
                        lastx = textView.getLeft();
                        lasty = textView.getTop();

                        // 重新设置位置，否则再次添加时候会位移。
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                        params.leftMargin = lastx;
                        params.topMargin = lasty;
                        textView.setLayoutParams(params);

                        break;
                    case MotionEvent.ACTION_MOVE:
                        isClick = true;//当按钮被移动的时候设置isclick为true
                        int x = (int) event.getRawX();
                        int y = (int) event.getRawY();
                        dx = x - startx;
                        dy = y - starty;
                        if (Math.abs(dx) > 10 || Math.abs(dy) > 10) {
                            textView.layout(textView.getLeft() + dx, textView.getTop() + dy, textView.getRight() + dx, textView.getBottom() + dy);
                            startx = (int) event.getRawX();
                            starty = (int) event.getRawY();
                            textView.invalidate();
                        }
                        break;
                }
                return isClick;
            }
        });
    }

    // 纯文字版本
    private void addTextView(ToFragmentMsg msg) {
        TextView textView = new TextView(getActivity());
        textView.setText(msg.text);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(msg.size);
        textView.setTag(textViews.size());
        textView.setPadding(5, 5, 5, 5);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        textView.setLayoutParams(lp);
        rlMb.addView(textView);
        eventMsg = new ToActivityMsg();
        eventMsg.isViewId = true;
        eventMsg.viewId = textViews.size();
        EventBusUtils.post(eventMsg);
        textViews.add(textView);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("--", "点击了" + ((int) textView.getTag()));

                toShowPop((int) textView.getTag(), 1);
            }
        });
        touchTextView(textView);
    }


    /**
     * 下面为图片互换
     */

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        int currentX = (int) event.x;
        int currentY = (int) event.y;
        position1 = getCurrentView(currentX, currentY);
        if (null != mList.get(position0).v && null != mList.get(position1).v) {
            if (position0 != position1) {
                changeImage(position0, position1);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEventCurrent event) {
        int mLastX = (int) event.mLastX;
        int mLastY = (int) event.mLastY;
        position0 = getCurrentView(mLastX, mLastY);


    }

    private int getCurrentView(int mLastX, int mLastY) {
        for (int i = 0; i < mList.size(); i++) {
            RectF displayRect = mList.get(i).v.getDisplayRect();
            int imgLeft = mList.get(i).imgLeft;
            int imgRight = mList.get(i).imgRight;
            int imgbottom = mList.get(i).imgbottom;
            int imgrightBo = mList.get(i).imgrightBo;
            if (mLastX >= imgLeft && mLastX <= imgRight && mLastY >= imgbottom && mLastY <= imgrightBo) {
                return i;
            }

        }

        return -1;
    }

    private void changeImage(int position0, int position1) {
        String path = mList.get(position0).imgPath;
        mList.get(position0).imgPath = mList.get(position1).imgPath;
        mList.get(position1).imgPath = path;

        Glide.with(this)
                .load(mList.get(position0).imgPath)
                .into(mList.get(position0).v);
        Glide.with(this)
                .load(mList.get(position1).imgPath)
                .into(mList.get(position1).v);

    }

    private List<MyViewBean> mList = new ArrayList();
    private int position0;
    private int position1;

    private String path1;
    private String path2;

    // View宽，高
    public int[] getLocation(View v) {
        int[] loc = new int[4];
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        loc[0] = location[0];
        loc[1] = location[1];
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(w, h);

        loc[2] = v.getMeasuredWidth();
        loc[3] = v.getMeasuredHeight();

        return loc;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (image1 != null && image2 != null) {
            int[] location2 = getLocation(image1);//获取在整个屏幕内的绝对坐标
            MyViewBean myViewBean2 = new MyViewBean();
            myViewBean2.imgLeft = location2[0];
            myViewBean2.imgbottom = location2[1];
            myViewBean2.imgRight = location2[0] + image1.getWidth();
            myViewBean2.imgrightBo = location2[1] + image1.getHeight();
            myViewBean2.v = image1;
            myViewBean2.imgPath = path1;
            mList.add(myViewBean2);


            int[] location = getLocation(image2);//获取在整个屏幕内的绝对坐标
            MyViewBean myViewBean = new MyViewBean();
            myViewBean.imgLeft = location[0];
            myViewBean.imgbottom = location[1];
            myViewBean.imgRight = location[0] + image2.getWidth();
            myViewBean.imgrightBo = location[1] + image2.getHeight();
            myViewBean.v = image2;
            myViewBean.imgPath = path2;
            mList.add(myViewBean);

        }
    }
}
