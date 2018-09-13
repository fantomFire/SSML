package zhonghuass.ssml.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/6/20.
 */

public class CustomSelectDialog extends Dialog {
    private static int default_width = 200; //默认宽度
    private static int default_height = 150;//默认高度
    public CustomSelectDialog(Context context, View layout, int style) {
        this(context, default_width, default_height, layout, style);
    }

    public CustomSelectDialog(Context context, int width, int height, View layout, int style) {

        super(context, style);

        setContentView(layout);

        Window window = getWindow();

        WindowManager.LayoutParams params = window.getAttributes();

        params.gravity = Gravity.CENTER;

        window.setAttributes(params);

    }
}
