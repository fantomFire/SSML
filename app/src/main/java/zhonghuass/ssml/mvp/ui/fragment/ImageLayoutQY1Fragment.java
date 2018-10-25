package zhonghuass.ssml.mvp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.*;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;

import android.widget.*;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import zhonghuass.ssml.di.component.DaggerImageLayoutQY1Component;
import zhonghuass.ssml.di.module.ImageLayoutQY1Module;
import zhonghuass.ssml.mvp.ToActivityMsg;
import zhonghuass.ssml.mvp.ToFragmentMsg;
import zhonghuass.ssml.mvp.contract.ImageLayoutQY1Contract;
import zhonghuass.ssml.mvp.presenter.ImageLayoutQY1Presenter;

import zhonghuass.ssml.R;
import zhonghuass.ssml.utils.EventBusUtils;
import zhonghuass.ssml.utils.image.PhotoView;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ImageLayoutQY1Fragment extends BaseFragment<ImageLayoutQY1Presenter> implements ImageLayoutQY1Contract.View {

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


    private ToActivityMsg eventMsg;
    private boolean isClick;
    private boolean isRotate;
    int defaultHeight, defaultWidth;// 屏幕宽高
    private static List<LocalMedia> selectList;
    private PopupWindow imageMenuPop;
    private String path;

    public static ImageLayoutQY1Fragment newInstance() {
        ImageLayoutQY1Fragment fragment = new ImageLayoutQY1Fragment();
        return fragment;
    }

    public static ImageLayoutQY1Fragment newInstance(List<LocalMedia> imgList) {
        ImageLayoutQY1Fragment fragment = new ImageLayoutQY1Fragment();
        selectList = imgList;
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerImageLayoutQY1Component //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .imageLayoutQY1Module(new ImageLayoutQY1Module(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_layout_qy1, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initBackPopupWindow();
        image1.setScaleType(ImageView.ScaleType.CENTER);

        path = selectList.get(0).getPath();
        Glide.with(this)
                .load(path)
                .into(image1);

        //把模板中默认的textView添加进去，因为点击之后要弹出底部菜单修改
        textViews.add(tv1);


        //获取屏幕宽高
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        defaultWidth = size.x;
        defaultHeight = size.y;


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

    @OnClick({R.id.rl_mb, R.id.iv1, R.id.tv1})
    public void onViewClicked(View view) {
        closeTextViewMenu();
        switch (view.getId()) {
            case R.id.rl_mb:
                break;
            case R.id.iv1:
                clickImageView = image1;
                clickIVNum = 0;
                imageMenuPop.showAsDropDown(image1);
                break;
            case R.id.tv1:
                toShowPop(0, 1);
                break;
        }
    }

    private PhotoView clickImageView;
    private int clickIVNum;//标记点击的是那个iv
    private int rotateNum1 = 0;//点击旋转的次数，每次旋转90°
    private int maxNum = 0;//点击放大次数

    private void initBackPopupWindow() {
        View contentview = getLayoutInflater().inflate(R.layout.popupwindow_image_menu, null);
        contentview.setFocusable(true); // 这个很重要
        contentview.setFocusableInTouchMode(true);

        imageMenuPop = new PopupWindow(contentview, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        TextView tvChange = contentview.findViewById(R.id.tv_change);
        TextView tvRotate = contentview.findViewById(R.id.tv_rotate);
        TextView tvAmplify = contentview.findViewById(R.id.tv_amplify);
        TextView tvReduce = contentview.findViewById(R.id.tv_reduce);

        // 替换
        tvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择图片
                selectPhoto(PictureMimeType.ofImage(), 1);
            }
        });


        // 旋转
        tvRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rotateNum1++;
                Bitmap bmp = BitmapFactory.decodeFile(path);
                Bitmap newBmp = rotaingImageView(90 * rotateNum1, bmp);
                clickImageView.setImageBitmap(newBmp);

            }
        });

        // 放大
        tvAmplify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float n = (float) ((maxNum + 1) * 0.25 + 1);
                if (n > 3) {
                    Toast.makeText(getActivity(), "不能再大啦！", Toast.LENGTH_SHORT).show();
                    return;
                }
                clickImageView.setScale(n,
                        (clickImageView.getRight()) / 2,
                        (contentview.getBottom()) / 2,
                        true);
                maxNum++;

            }
        });

        // 缩小
        tvReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float n = (float) ((maxNum - 1) * 0.25 + 1);
                if (n < 1) {
                    Toast.makeText(getActivity(), "不能再小啦！", Toast.LENGTH_SHORT).show();
                    return;
                }
                clickImageView.setScale(n,
                        (clickImageView.getRight()) / 2,
                        (contentview.getBottom()) / 2,
                        true);
                maxNum--;

            }
        });

        imageMenuPop.setBackgroundDrawable(new ColorDrawable(0x11111111));
        // 设置好参数之后再show
        imageMenuPop.setOutsideTouchable(true);


    }

    /**
     * 自适应图片的ImageView(根据屏幕宽度适应)
     */
    public void setImageViewMathParent(Activity context,
                                       PhotoView view, Bitmap bitmap) {
        //获得屏幕密度
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay()
                .getMetrics(displayMetrics);
        //获得屏幕宽度和图片宽度的比例
        float scalew = (float) displayMetrics.widthPixels
                / (float) bitmap.getWidth();
        //获得ImageView的参数类
        ViewGroup.LayoutParams vgl = view.getLayoutParams();
        //设置ImageView的宽度为屏幕的宽度
        vgl.width = displayMetrics.widthPixels;
        //设置ImageView的高度
        vgl.height = (int) (bitmap.getHeight() * scalew);

        view.setLayoutParams(vgl);
        view.setImageBitmap(bitmap);
        //等比例缩放
        view.setAdjustViewBounds(true);
        //设置图片充满ImageView控件
        view.setScaleType(ImageView.ScaleType.CENTER);

        if (bitmap != null && bitmap.isRecycled()) {
            bitmap.recycle();
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
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                path = selectList.get(0).getPath();
                Glide.with(this)
                        .load(path)
                        .into(image1);
            }
        }
    }

    /*
     * 旋转缩放图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public Bitmap rotaingImageView(int angle, Bitmap bitmap) {


        float scaleWidth = (float) clickImageView.getWidth() / bitmap.getWidth();
        float scaleHeight = (float) clickImageView.getHeight() / bitmap.getHeight();

        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);


        //因为ImageView.ScaleType.CENTER默认保证原图大小不变的情况下，按比例将图片宽或者高让等于view的宽高来缩放
        //不设置下面的缩放，图片旋转之后会ScaleType.CENTER失效。图片变成原来大小。
        if (bitmap.getHeight() >= bitmap.getWidth()) { //如果图片是竖直方向的
            matrix.postScale(scaleWidth, scaleWidth);
        }
        if (bitmap.getHeight() < bitmap.getWidth()) {//如果图片是水平方向的
            matrix.postScale(scaleHeight, scaleHeight);
        }

        // 创建新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


    //通知编辑页面弹出底部菜单 pop 用来区分是tag还是text
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
        RelativeLayout llTag = (RelativeLayout) view.findViewById(R.id.ll_tag);
        ImageView ivTag = (ImageView) view.findViewById(R.id.iv_tag);
        Log.e("--", "IV: " + msg.type);
        ivTag.setImageResource(msg.type);
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

}
