package com.github.library.layoutView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.library.R;


/**
 * Created by 苏坡坡要吃婆婆酥 on 2018 2018/3/3 .
 * Toolbar自定义强化版 带搜索框，左右图标，中间标题
 * app:RightImgButtonIcon="@mipmap/image_indicator_arrow_right"
 * app:LeftImgButtonIcon="@mipmap/image_indicator_arrow_left"
 * app:title="标题"
 * app:isShowEditText="true"
 * app:editHint="请输入关键字"
 */

public class ToolbarEx extends Toolbar implements TextWatcher {
    private EditText toolbar_editText;
    private TextView toolbar_search;
    private TextView toolbar_textView;
    private ImageButton toolbar_rightBtn;
    private ImageButton toolbar_leftBtn;
    private MyToolBarBtnListenter btnListenter;
    private MyToolBarEditTextListener editTextListener;

    public ToolbarEx(Context context) {
        this(context, null);
    }

    public ToolbarEx(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.toolbarStyle);
    }

    public ToolbarEx(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        //        setContentInsetsRelative(100,100);

        final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                R.styleable.ToolBarEx, defStyleAttr, 0);
        Drawable drawableRight = a.getDrawable(R.styleable.ToolBarEx_RightImgButtonIcon);
        Drawable drawableLeft = a.getDrawable(R.styleable.ToolBarEx_LeftImgButtonIcon);
        boolean aBoolean = a.getBoolean(R.styleable.ToolBarEx_isShowEditText, false);
        String hint = a.getString(R.styleable.ToolBarEx_editHint);
        if(!TextUtils.isEmpty(hint)){
            toolbar_editText.setHint(hint);
        }
        if (drawableRight != null) {
            setRightImageBtnDrawable(drawableRight);
        }
        if (drawableLeft != null) {
            setLeftImageBtnDrawable(drawableLeft);
        }

        /**
         * 如果显示editText为true，则把editText 设为显示，TextView设为隐藏
         */
        if (aBoolean) {
            showEditText();
            hintTextView();
        } else {
            hintEditText();
        }

    }


    private void initView() {
        View view = View.inflate(getContext(), R.layout.layout_toolbar_ex, null);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
        addView(view, params);

        toolbar_editText = (EditText) this.findViewById(R.id.toolbar_editText);
        toolbar_textView = (TextView) this.findViewById(R.id.toolbar_textView);
        toolbar_rightBtn = (ImageButton) this.findViewById(R.id.toolbar_rightBtn);
        toolbar_leftBtn = (ImageButton) this.findViewById(R.id.toolbar_leftBtn);
        toolbar_search = (TextView) this.findViewById(R.id.toolbar_search);
        toolbar_editText.addTextChangedListener(this);
        toolbar_rightBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != btnListenter) {
                    btnListenter.ImageRightBtnclick();
                }
            }
        });

        toolbar_leftBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != btnListenter) {
                    btnListenter.ImageLeftBtnclick();
                }

            }
        });
        toolbar_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != btnListenter) {
                    if(TextUtils.isEmpty(toolbar_editText.getText())){
                        Toast.makeText(getContext(),"输入为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    btnListenter.searchBtnclick(toolbar_editText.getText().toString());
                }
            }
        });
        toolbar_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // 失去焦点
                    toolbar_editText.setCursorVisible(false);
                    InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(toolbar_editText.getWindowToken(), 0);
                }else{
                    toolbar_editText.setCursorVisible(true);
                }
            }
        });

    }

    public void showTextView() {
        toolbar_textView.setVisibility(View.VISIBLE);
    }

    public void showEditText() {
        toolbar_editText.setVisibility(View.VISIBLE);
    }

    public void showRightBtnIcon() {
        toolbar_rightBtn.setVisibility(View.VISIBLE);
    }

    public void showLeftBtnIcon() {
        toolbar_leftBtn.setVisibility(View.VISIBLE);
    }

    public void showToolbar_search(){
        toolbar_search.setVisibility(View.VISIBLE);
    }

    public void hintToolbar_search(){
        toolbar_search.setVisibility(View.GONE);
    }
    public void hintTextView() {
        toolbar_textView.setVisibility(View.GONE);
    }

    public void hintEditText() {
        toolbar_editText.setVisibility(View.GONE);
    }

    public void hintRightBtnIcon() {
        toolbar_rightBtn.setVisibility(View.INVISIBLE);
    }




    @Override
    public void setTitle(@StringRes int resId) {
        showTextView();
        if (resId != 0) {
            toolbar_textView.setText(resId);
        }
    }
    @Override
    public void setTitle(CharSequence title) {
        initView();
        showTextView();
        if (title != null) {
            toolbar_textView.setText(title);
        }
    }

    public void setLeftImageBtnDrawable(Drawable resId) {
        showLeftBtnIcon();
        toolbar_leftBtn.setImageDrawable(resId);
    }

    public void setLeftImageBtnDrawable(int resId) {
        showLeftBtnIcon();
        toolbar_leftBtn.setImageResource(resId);
    }

    public void setRightImageBtnDrawable(Drawable resId) {
        showRightBtnIcon();
        toolbar_rightBtn.setImageDrawable(resId);
    }

    public void setRightImageBtnResource(int resId) {
        showRightBtnIcon();
        toolbar_rightBtn.setImageResource(resId);
    }


    public void setMyToolBarBtnListenter(MyToolBarBtnListenter btnListenter) {
        this.btnListenter = btnListenter;
    }

    public void setMyToolBarEditTextListener(MyToolBarEditTextListener editTextListener) {
        this.editTextListener = editTextListener;
    }

    /**
     * Log.i(TAG, "输入文本之前的状态");
     *
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (null != editTextListener) {
            editTextListener.beforeTextChanged(s, start, count, after);
        }
    }

    /**
     * Log.i(TAG, "输入文字中的状态，");
     *
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (null != editTextListener) {
            editTextListener.onTextChanged(s, start, before, count);
        }
    }

    /**
     * Log.i(TAG, "输入文字后的状态");
     *
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {
        if(TextUtils.isEmpty(s)){
            showRightBtnIcon();
            hintToolbar_search();
        }else {
            hintRightBtnIcon();
            showToolbar_search();
        }
        if (null != editTextListener) {
            editTextListener.afterTextChanged(s);
        }
    }

    public interface MyToolBarBtnListenter {
        void ImageRightBtnclick();
        void ImageLeftBtnclick();
        void searchBtnclick(String content);
    }

    public interface MyToolBarEditTextListener {
        void beforeTextChanged(CharSequence s, int start, int count, int after);

        void onTextChanged(CharSequence s, int start, int before, int count);

        void afterTextChanged(Editable s);
    }
}
