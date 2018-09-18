package zhonghuass.ssml.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerImageEditorComponent;
import zhonghuass.ssml.di.module.ImageEditorModule;
import zhonghuass.ssml.mvp.ToActivityMsg;
import zhonghuass.ssml.mvp.ToFragmentMsg;
import zhonghuass.ssml.mvp.contract.ImageEditorContract;
import zhonghuass.ssml.mvp.presenter.ImageEditorPresenter;
import zhonghuass.ssml.mvp.ui.fragment.ImageLayout1Fragment;
import zhonghuass.ssml.mvp.ui.fragment.ImageLayout2Fragment;
import zhonghuass.ssml.utils.*;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ImageEditorActivity extends BaseActivity<ImageEditorPresenter> implements ImageEditorContract.View {

    @BindView(R.id.rl_edit)
    RelativeLayout relativeLayout;
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
    @BindView(R.id.ll_menu)
    LinearLayout llMenu;
    @BindView(R.id.fl_image)
    FrameLayout flImage;
    private List<Integer> mList;
    private PopupWindow backPopupWindow, fontPopupWindow, tagPopupWindow;
    private int textSize = 15;
    private int fragment = 1;
    private int viewId;
    private int textColor = R.color.black;

    private Integer[] sizes = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    private Integer[] layouts = {R.layout.activity_my_setting};
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
    private List<Fragment> mFragments;
    private FragmentManager fm;
    private EditText etFont;

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


//        try {
//            int layoutResID = initView(savedInstanceState);
//            //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
//            if (layoutResID != 0) {
//                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View contentView = inflater.inflate(layoutResID, null);
//
//                flImage.addView(contentView);
//                //setContentView(layoutResID);
//                //绑定到butterknife
//                Unbinder mUnbinder = ButterKnife.bind(this);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        mList = Arrays.asList(colors);
        initBackPopupWindow();
        initFontPopupWindow();
        initTagPopupWindow();


        ImageLayout1Fragment image1 = ImageLayout1Fragment.newInstance();
        ImageLayout2Fragment image2 = ImageLayout2Fragment.newInstance();
        mFragments = new ArrayList<>();
        mFragments.add(image1);
        mFragments.add(image2);
        fm = getSupportFragmentManager();
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
        for (int i = 0; i < 17; i++) {
            View radioButton = getLayoutInflater().inflate(R.layout.item_edit_bg_1, null);
            CircleImageView civ = radioButton.findViewById(R.id.civ);
            civ.setImageResource(mList.get(i));
            if (i == 6) {
                radioButton.setPadding(40, 0, 0, 0);
            }
            radioButton.setTag(i);
            crg.addView(radioButton);
        }
        crg.setListener(new CustomRadioGroup.OnclickListener() {
            @Override
            public void OnText(int position) {
                outBg.setBackgroundColor(getResources().getColor(colors[position]));
                switch (imageLayout) {
                    case 0:
                        ImageLayout1Fragment image1 = (ImageLayout1Fragment) fm.getFragments().get(0);
                        image1.rlBg.setBackgroundColor(getResources().getColor(colors[position]));
                        break;
                    case -1:
                        ImageLayout2Fragment image2 = (ImageLayout2Fragment) fm.getFragments().get(0);
                        image2.rlBg.setBackgroundColor(getResources().getColor(colors[position]));
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
        for (int i = 0; i < mList.size(); i++) {
            View radioButton = getLayoutInflater().inflate(R.layout.item_edit_bg_1, null);
            CircleImageView civ = radioButton.findViewById(R.id.civ);
            civ.setImageResource(mList.get(i));
            if (i == 6 || i == 17) {
                radioButton.setPadding(40, 0, 0, 0);
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
                sv.setVisibility(View.VISIBLE);
                crg.setVisibility(View.GONE);
                sv2.setVisibility(View.GONE);
            }
        });
        tvSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv2.setVisibility(View.VISIBLE);
                crg.setVisibility(View.GONE);
                sv.setVisibility(View.GONE);
            }
        });
        tvColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        LinearLayout llClose = contentview.findViewById(R.id.ll_close);
        llClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagPopupWindow.dismiss();
            }
        });
        LinearLayout llTag = contentview.findViewById(R.id.ll_tag);
        EditText etTag = contentview.findViewById(R.id.et_tag);
        RadioGroup rg = contentview.findViewById(R.id.rg2);

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
                    llTag.setBackgroundColor(getResources().getColor(colors[position]));
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

    @OnClick({R.id.edit_img, R.id.moban_item, R.id.back_item, R.id.name_item, R.id.mark_item})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edit_img:
                break;
            case R.id.moban_item:
                FragmentUtils.removeAllFragments(fm);
                imageLayout++;
                initImageLayout(mFragments.get(imageLayout));
                if (imageLayout > 0) {
                    imageLayout = -1;
                    fragment = 1;
                }
                break;
            case R.id.back_item:
                backPopupWindow.showAtLocation(relativeLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.name_item:
                ToFragmentMsg msg = new ToFragmentMsg();
                msg.fragment = fragment;
                msg.text = etFont.getText().toString();
                msg.size = 15;
                msg.isAddText = true;
                EventBusUtils.post(msg);
                fontPopupWindow.showAtLocation(relativeLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.mark_item:
                tagPopupWindow.showAtLocation(relativeLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
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
            viewId = msg.viewId;
            fragment = msg.fragment;
        }
        if (msg.isViewId) {
            viewId = msg.viewId;
        }
    }
}
