package zhonghuass.ssml.mvp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.zhangke.websocket.AbsWebSocketActivity;
import com.zhangke.websocket.ErrorResponse;
import com.zhangke.websocket.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhonghuass.ssml.R;
import zhonghuass.ssml.utils.mywebsocket.CommonResponse;

public class ChattingActivity extends AbsWebSocketActivity {

    @BindView(R.id.rv_sms)
    RecyclerView rvSms;
    @BindView(R.id.btn_add_emoji)
    ImageView btnAddEmoji;
    @BindView(R.id.edtSms)
    EditText edtSms;
    @BindView(R.id.btn_add_photo)
    ImageView btnAddPhoto;
    @BindView(R.id.btnSms)
    Button btnSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        ButterKnife.bind(this);
        JSONObject parameters = new JSONObject();
        parameters.put("message_type", "register");
        parameters.put("member", "1_1");
        parameters.put("user_agent", "android");
        sendText(parameters.toJSONString());
    }

    @Override
    public void onMessageResponse(Response response) {
        System.out.println("连接成功"+((CommonResponse)response).getResponseEntity().getMessage());

    }

    @Override
    public void onSendMessageError(ErrorResponse errorResponse) {
        System.out.println("连接失败"+errorResponse.getDescription());
    }
}
