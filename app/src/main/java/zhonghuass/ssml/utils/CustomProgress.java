package zhonghuass.ssml.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * 自定义Progress
 */

public class CustomProgress extends ProgressBar {
    public ProgressDialog dialog;

    public CustomProgress(Context context) {
        super(context);
        if (dialog == null && context != null) {
            dialog = new ProgressDialog(context);
            dialog.setMessage("正在拼命加载！~");
            dialog.setCancelable(false);
        }
    }

    public CustomProgress(Context context, String message) {
        super(context);
        if (dialog == null && context != null) {
            dialog = new ProgressDialog(context);
            dialog.setMessage(message);
            dialog.setCancelable(false);
        }
    }

    public CustomProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void show() {
        if (dialog != null) {
            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog != null) {
            // 为什么要延时呢？如果网速好的情况下太快，用户体验不好。
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            }, 500);
        }
    }


}
