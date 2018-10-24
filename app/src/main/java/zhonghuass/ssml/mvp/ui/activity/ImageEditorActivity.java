package zhonghuass.ssml.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import butterknife.BindView;
import butterknife.OnClick;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.entity.LocalMedia;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerImageEditorComponent;
import zhonghuass.ssml.di.module.ImageEditorModule;
import zhonghuass.ssml.mvp.ToActivityMsg;
import zhonghuass.ssml.mvp.ToFragmentMsg;
import zhonghuass.ssml.mvp.contract.ImageEditorContract;
import zhonghuass.ssml.mvp.presenter.ImageEditorPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.mvp.ui.fragment.ImageLayout1Fragment;
import zhonghuass.ssml.mvp.ui.fragment.ImageLayout2Fragment;
import zhonghuass.ssml.utils.CircleImageView;
import zhonghuass.ssml.utils.CustomRadioGroup;
import zhonghuass.ssml.utils.EventBusUtils;
import zhonghuass.ssml.utils.FragmentUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ImageEditorActivity extends MBaseActivity<ImageEditorPresenter> implements ImageEditorContract.View {

    @BindView(R.id.rl_edit)
    RelativeLayout relativeLayout;
    @BindView(R.id.moban_item)
    LinearLayout mobanItem;
    @BindView(R.id.back_item)
    LinearLayout backItem;
    @BindView(R.id.text_item)
    LinearLayout nameItem;
    @BindView(R.id.tag_item)
    LinearLayout markItem;
    @BindView(R.id.ll_menu)
    LinearLayout llMenu;
    @BindView(R.id.fl_image)
    FrameLayout flImage;
    private List<Integer> mList;
    private List<Integer> tagList, tagIconList;
    private PopupWindow backPopupWindow, fontPopupWindow, tagPopupWindow;
    private int textSize = 15;
    private int fragment = 1;
    private int viewId;
    private int textColor = R.color.gray;
    private int tagType;

    private Integer[] sizes = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    private Integer[] colors = {
            R.color.white, R.color.blue, R.color.red,
            R.color.white, R.color.blue, R.color.red,

            R.color.white, R.color.blue, R.color.red,
            R.color.blue, R.color.white,

            R.color.white, R.color.blue, R.color.red,
            R.color.white, R.color.blue, R.color.red,

            R.color.white, R.color.blue, R.color.red,
            R.color.blue, R.color.white
    };
    private Integer[] tagsIcon = {
            R.mipmap.edit_icon_1_on, R.mipmap.edit_icon_4_on

    };
    private Integer[] tags = {
            R.mipmap.edit_1a,
            R.mipmap.edit_1a
    };
    private List<Fragment> mFragments;
    private FragmentManager fm;
    private EditText etFont;
    private ImageLayout1Fragment fragment1;
    private ImageLayout2Fragment fragment2;
    private List<LocalMedia> selectList;
    private EditText etTag;

    public interface IOnFocusListenable {
        public void onWindowFocusChanged(boolean hasFocus);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (fragment1 instanceof IOnFocusListenable) {
            ((IOnFocusListenable) fragment1).onWindowFocusChanged(hasFocus);
        }
    }

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
        initToolBar("图片编辑", true, "保存");
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
            }
        });


        Bundle mBundle = getIntent().getExtras();
        int imgMB = mBundle.getInt("template_num", 0);
        selectList = mBundle.getParcelableArrayList("imageList");
        // 初始化图片模板
        initFragment(imgMB);


        mList = Arrays.asList(colors);
        tagList = Arrays.asList(tags);
        tagIconList = Arrays.asList(tagsIcon);
        initBackPopupWindow();
        initFontPopupWindow();
        initTagPopupWindow();


    }

    private void initFragment(int imgMB) {
        fm = getSupportFragmentManager();
        switch (imgMB) {
            case 1://两张张图模板
                fragment1 = ImageLayout1Fragment.newInstance(selectList);
                initImageLayout(fragment1);
                break;
            case 0://一张图模板
                fragment2 = ImageLayout2Fragment.newInstance(selectList);
                initImageLayout(fragment2);
                break;
        }
        imageLayout = imgMB;
    }


    /**
     * 判断软键盘是否弹出
     */
    public static boolean isSHowKeyboard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        if (imm.hideSoftInputFromWindow(v.getWindowToken(), 0)) {
            imm.showSoftInput(v, 0);
            return true;
            //软键盘已弹出
        } else {
            return false;
            //软键盘未弹出
        }
    }

    private void initBackPopupWindow() {
        //LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentview = getLayoutInflater().inflate(R.layout.popupwindow_edit_bg, null);
        contentview.setFocusable(true); // 这个很重要
        contentview.setFocusableInTouchMode(true);

        backPopupWindow = new PopupWindow(contentview, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);


        backPopupWindow.setBackgroundDrawable(new ColorDrawable(0x11111111));
        // 设置好参数之后再show
        backPopupWindow.setOutsideTouchable(true);

        LinearLayout llClose = contentview.findViewById(R.id.ll_close);
        llClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backPopupWindow.dismiss();
            }
        });
        CustomRadioGroup crg = contentview.findViewById(R.id.rg);
        RadioGroup rg = contentview.findViewById(R.id.rg2);
        int leftPx = dip2px(ImageEditorActivity.this, itemW / 2);

        for (int i = 0; i < 17; i++) {
            View radioButton = getLayoutInflater().inflate(R.layout.item_edit_bg_1, null);
            CircleImageView civ = radioButton.findViewById(R.id.civ);
            civ.setImageResource(mList.get(i));

            if (i == 6) {
                radioButton.setPadding(leftPx + (crg.horizontalSpacing) / 2, 0, 0, 0);
            }
            radioButton.setTag(i);
            crg.addView(radioButton);
        }
        crg.setListener(new CustomRadioGroup.OnclickListener() {
            @Override
            public void OnText(int position) {
//                outBg.setBackgroundColor(getResources().getColor(colors[position]));
                switch (imageLayout) {
                    case 0:
                        ImageLayout2Fragment image1 = (ImageLayout2Fragment) fm.getFragments().get(0);
                        image1.rlBg.setBackgroundColor(getResources().getColor(colors[position]));
                        break;
                }
            }
        });
        for (int i = 0; i < mList.size(); i++) {
            View radioButton = getLayoutInflater().inflate(R.layout.item_edit_bg_1, null);
            CircleImageView civ = radioButton.findViewById(R.id.civ);
            civ.setImageResource(mList.get(i));
            radioButton.setTag(i);
            radioButton.setPadding(5, 0, 5, 0);
            rg.addView(radioButton);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    Log.e("--", "" + position);
                }
            });
        }

    }

    public int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private int itemW = 40;

    private void initFontPopupWindow() {
        //LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentview = getLayoutInflater().inflate(R.layout.popupwindow_edit_font, null);
        contentview.setFocusable(true); // 这个很重要
        contentview.setFocusableInTouchMode(true);

        fontPopupWindow = new PopupWindow(contentview, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);


        fontPopupWindow.setBackgroundDrawable(new ColorDrawable(0x11111111));
        // 设置好参数之后再show
        fontPopupWindow.setOutsideTouchable(true);

        //设置弹出窗体需要软键盘，
        fontPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //再设置模式，和Activity的一样，覆盖，调整大小。
        fontPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        fontPopupWindow.setFocusable(true);


        etFont = contentview.findViewById(R.id.et_context);

        ImageView ivOk = contentview.findViewById(R.id.iv_ok);
        ImageView ivShow = contentview.findViewById(R.id.iv_showspan);

        TextView tvFont = contentview.findViewById(R.id.tv_font);
        TextView tvColor = contentview.findViewById(R.id.tv_color);
        TextView tvSize = contentview.findViewById(R.id.tv_size);

        CustomRadioGroup crg = contentview.findViewById(R.id.crg);
        ScrollView sv = contentview.findViewById(R.id.scroll_view);
        ScrollView sv2 = contentview.findViewById(R.id.scroll_view2);
        RadioGroup rg = contentview.findViewById(R.id.rg);
        RadioGroup rg2 = contentview.findViewById(R.id.rg2);

        int leftPx = dip2px(ImageEditorActivity.this, itemW / 2);
        for (int i = 0; i < mList.size(); i++) {
            View radioButton = getLayoutInflater().inflate(R.layout.item_edit_bg_1, null);
            CircleImageView civ = radioButton.findViewById(R.id.civ);
            civ.setImageResource(mList.get(i));
            if (i == 6 || i == 17) {
                radioButton.setPadding(leftPx + (crg.horizontalSpacing) / 2, 0, 0, 0);
            }
            radioButton.setTag(i);
            crg.addView(radioButton);
        }
        crg.setListener(new CustomRadioGroup.OnclickListener() {
            @Override
            public void OnText(int position) {
                crg.getChildAt(position).setSelected(true);
                textColor = colors[position];
                etFont.setTextColor(getResources().getColor(textColor));
//                CircleImageView civ = crg.getChildAt(position).findViewById(R.id.civ);
//                civ.setImageResource(R.color.black);
            }
        });
        // 字体类型
        for (int i = 0; i < mList.size(); i++) {
            TextView textView = new TextView(this);
            textView.setTag(i);
            textView.setText("床前明月光，地上鞋两双。");
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(16);
            textView.setPadding(50, 10, 10, 10);
            rg.addView(textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    Log.e("--", "" + position);
                }
            });
        }
        // 字体大小
        for (int i = 0; i < sizes.length; i++) {
            TextView textView = new TextView(this);
            textView.setTag(i);
            textView.setText("床前明月光，地上鞋两双。");
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(sizes[i]);
            textView.setPadding(50, 10, 10, 10);
            rg2.addView(textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    textSize = sizes[position];
                    etFont.setTextSize(textSize);
                }
            });
        }

        tvFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawableLeft = getResources().getDrawable(
                        R.mipmap.fb_icon_24);
                tvFont.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                        null, null, null);
                tvFont.setCompoundDrawablePadding(10);
                tvFont.setTextColor(Color.GRAY);


                Drawable drawableLeft2 = getResources().getDrawable(
                        R.mipmap.fb_icon_22);
                tvSize.setCompoundDrawablesWithIntrinsicBounds(drawableLeft2,
                        null, null, null);
                tvSize.setCompoundDrawablePadding(10);
                tvSize.setTextColor(Color.WHITE);


                Drawable drawableLeft3 = getResources().getDrawable(
                        R.mipmap.fb_icon_25);
                tvColor.setCompoundDrawablesWithIntrinsicBounds(drawableLeft3,
                        null, null, null);
                tvColor.setCompoundDrawablePadding(10);
                tvColor.setTextColor(Color.WHITE);


                sv.setVisibility(View.VISIBLE);
                crg.setVisibility(View.GONE);
                sv2.setVisibility(View.GONE);
            }
        });
        tvSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawableLeft = getResources().getDrawable(
                        R.mipmap.fb_icon_20);
                tvFont.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                        null, null, null);
                tvFont.setCompoundDrawablePadding(10);
                tvFont.setTextColor(Color.WHITE);


                Drawable drawableLeft2 = getResources().getDrawable(
                        R.mipmap.fb_icon_23);
                tvSize.setCompoundDrawablesWithIntrinsicBounds(drawableLeft2,
                        null, null, null);
                tvSize.setCompoundDrawablePadding(10);
                tvSize.setTextColor(Color.GRAY);


                Drawable drawableLeft3 = getResources().getDrawable(
                        R.mipmap.fb_icon_25);
                tvColor.setCompoundDrawablesWithIntrinsicBounds(drawableLeft3,
                        null, null, null);
                tvColor.setCompoundDrawablePadding(10);
                tvColor.setTextColor(Color.WHITE);


                sv2.setVisibility(View.VISIBLE);
                crg.setVisibility(View.GONE);
                sv.setVisibility(View.GONE);
            }
        });
        tvColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawableLeft = getResources().getDrawable(
                        R.mipmap.fb_icon_20);
                tvFont.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                        null, null, null);
                tvFont.setCompoundDrawablePadding(10);
                tvFont.setTextColor(Color.WHITE);

                Drawable drawableLeft2 = getResources().getDrawable(
                        R.mipmap.fb_icon_22);
                tvSize.setCompoundDrawablesWithIntrinsicBounds(drawableLeft2,
                        null, null, null);
                tvSize.setCompoundDrawablePadding(10);
                tvSize.setTextColor(Color.WHITE);


                Drawable drawableLeft3 = getResources().getDrawable(
                        R.mipmap.fb_icon_21);
                tvColor.setCompoundDrawablesWithIntrinsicBounds(drawableLeft3,
                        null, null, null);
                tvColor.setCompoundDrawablePadding(10);
                tvColor.setTextColor(Color.GRAY);


                crg.setVisibility(View.VISIBLE);
                sv.setVisibility(View.GONE);
                sv2.setVisibility(View.GONE);
            }
        });

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        ivShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSHowKeyboard(ImageEditorActivity.this, etFont)) {
                    imm.hideSoftInputFromWindow(etFont.getWindowToken(), 0);
                } else {
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
        ivOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToFragmentMsg msg = new ToFragmentMsg();
                msg.text = etFont.getText().toString();
                msg.fragment = fragment;
                msg.viewId = viewId;
                msg.color = textColor;
                msg.size = textSize;
                msg.isFontOk = true;
                EventBusUtils.post(msg);
                fontPopupWindow.dismiss();
            }
        });

    }

    private void initTagPopupWindow() {
        View contentview = getLayoutInflater().inflate(R.layout.popupwindow_edit_tag, null);
        contentview.setFocusable(true); // 这个很重要
        contentview.setFocusableInTouchMode(true);

        tagPopupWindow = new PopupWindow(contentview, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);


        tagPopupWindow.setBackgroundDrawable(new ColorDrawable(0x11111111));
        // 设置好参数之后再show
        tagPopupWindow.setOutsideTouchable(true);

        //设置弹出窗体需要软键盘，
        tagPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //再设置模式，和Activity的一样，覆盖，调整大小。
        tagPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        tagPopupWindow.setFocusable(true);

        tagType = tags[0];

        ImageView ivClose = (ImageView) contentview.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagPopupWindow.dismiss();
            }
        });
        ImageView ivAdd = (ImageView) contentview.findViewById(R.id.iv_add);
        etTag = (EditText) contentview.findViewById(R.id.et_tag);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 关闭，添加
                ToFragmentMsg msg = new ToFragmentMsg();
                msg.text = etTag.getText().toString();
                Log.e("--", "ET: " + msg.type);
                msg.fragment = fragment;
                msg.viewId = viewId;
                msg.type = tagType;
                Log.e("--", "IV1: " + msg.type);
                msg.size = textSize;
                msg.isTagOk = true;
                EventBusUtils.post(msg);
                tagPopupWindow.dismiss();

            }
        });
        RelativeLayout llTag = contentview.findViewById(R.id.ll_tag);
        ImageView ivTag = contentview.findViewById(R.id.iv_tag);
        RadioGroup rg = contentview.findViewById(R.id.rg2);

        for (int i = 0; i < tagIconList.size(); i++) {
            View radioButton = getLayoutInflater().inflate(R.layout.item_edit_bg_1, null);
            CircleImageView civ = radioButton.findViewById(R.id.civ);
            civ.setImageResource(tagIconList.get(i));
            radioButton.setTag(i);
            radioButton.setPadding(5, 0, 5, 0);
            rg.addView(radioButton);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    tagType = tags[position];
                    ivTag.setImageResource(tagType);
                }
            });
        }

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
    }

    private void initImageLayout(Fragment fragment) {
        FragmentUtils.addFragment(fm, fragment, R.id.fl_image);
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


    private int imageLayout = -1;

    @OnClick({R.id.iv_back, R.id.moban_item, R.id.back_item, R.id.text_item, R.id.tag_item})
    public void onViewClicked(View view) {
        //发这消息是为了关闭TextView上的菜单
        ToFragmentMsg msg = new ToFragmentMsg();
        msg.fragment = fragment;
        EventBusUtils.post(msg);

        switch (view.getId()) {

            case R.id.iv_back:
                finish();
                break;
            case R.id.moban_item:
//                FragmentUtils.removeAllFragments(fm);
//                imageLayout++;
//                initImageLayout(mFragments.get(imageLayout));
//                if (imageLayout > 0) {
//                    imageLayout = -1;
//                    fragment = 1;
//                }
                Intent intent = new Intent();
                intent.setClass(ImageEditorActivity.this, SelectMBActivity.class);
                startActivityForResult(intent, 999);

                break;
            case R.id.back_item:
                backPopupWindow.showAtLocation(relativeLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.text_item:
                msg = new ToFragmentMsg();
                msg.fragment = fragment;
                msg.text = etFont.getText().toString();
                msg.size = 15;
                msg.color = textColor;
                msg.isAddText = true;
                EventBusUtils.post(msg);
                fontPopupWindow.showAtLocation(relativeLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.tag_item:
                tagPopupWindow.showAtLocation(relativeLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
        }
    }


    //保存图片
    private void saveImage() {
        Bitmap b = getBitmap();
        String SavePath = getSDCardPath() + "/SSMLImage";
        // 保存Bitmap
        try {
            File path = new File(SavePath);
            // 文件
            String imageName = (System.currentTimeMillis() / 1000) + ".jpg";
            String filepath = SavePath + "/SSML" + imageName;
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
                Toast.makeText(this, "图片已保存至SDCard/SSMLImage/下",
                        Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap getBitmap() {
        flImage.setDrawingCacheEnabled(true);
        flImage.buildDrawingCache();
        //启用DrawingCache并创建位图
        Bitmap bitmap = Bitmap.createBitmap(flImage.getDrawingCache());
        // 创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        flImage.setDrawingCacheEnabled(false);
        // 禁用DrawingCahce否则会影响性能
        return bitmap;
    }

    //获取SDCard的目录路径功能
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999) {
//            initImageLayout(mFragments.get(0));
//            imageLayout = 0;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusUtils.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtils.unregister(this);
    }

    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showMsg(ToActivityMsg msg) {
        if (msg.showFontPop) {
            fontPopupWindow.showAtLocation(relativeLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            etFont.setText(msg.text);
            etFont.setTextColor(msg.color);
            viewId = msg.viewId;
            fragment = msg.fragment;
        }
        if (msg.showTagPop) {
            tagPopupWindow.showAtLocation(relativeLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            etTag.setText(msg.text);
            viewId = msg.viewId;
            fragment = msg.fragment;
        }
        if (msg.isViewId) {
            viewId = msg.viewId;
        }
    }
}
