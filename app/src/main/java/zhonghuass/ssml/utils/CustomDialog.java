package zhonghuass.ssml.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.*;
import android.widget.TextView;
import zhonghuass.ssml.R;

/**
 * 自定义Dialog
 */

public class CustomDialog extends Dialog {
    private TextView tvTitle;
    private TextView tvContent;
    public TextView tvYes;
    public TextView tvNo;


    public CustomDialog(Context context) {
        super(context, R.style.MyDialog);
        View inflater = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
        setContentView(inflater);
        tvTitle = inflater.findViewById(R.id.tv_title);
        tvContent = inflater.findViewById(R.id.tv_content);
        tvYes = inflater.findViewById(R.id.tv_yes);
        tvNo = inflater.findViewById(R.id.tv_no);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setContent(String content) {
        tvContent.setText(content);
    }


}
