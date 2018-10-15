package zhonghuass.ssml.mvp.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhonghuass.ssml.R;

public class WebSocketActivity extends AppCompatActivity {


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
    private WebSocketClient mSocketClient;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            mContentTv.setText(mContentTv.getText() + "\n" + msg.obj);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //TODO 这里URL 别忘了切换到自己的IP
                    mSocketClient = new WebSocketClient(new URI("ws://video.zhonghuass.cn:10000")) {
                        @Override
                        public void onOpen(ServerHandshake handshakedata) {
                            Log.d("picher_log", "打开通道" + handshakedata.getHttpStatus());
                            if (mSocketClient != null) {
                                JSONObject parameters = new JSONObject();
                                parameters.put("message_type", "register");
                                parameters.put("member", "1_1");
                                parameters.put("user_agent", "android");
                                mSocketClient.send(parameters.toJSONString());
                            }
//                            handler.obtainMessage(0, message).sendToTarget();
                        }

                        @Override
                        public void onMessage(String message) {
                            Log.d("picher_log", "接收消息" + message);
                            handler.obtainMessage(0, message).sendToTarget();
                        }

                        @Override
                        public void onClose(int code, String reason, boolean remote) {
                            Log.d("picher_log", "通道关闭");
//                            handler.obtainMessage(0, message).sendToTarget();
                        }

                        @Override
                        public void onError(Exception ex) {
                            Log.d("picher_log", "链接错误");
                        }
                    };
                    mSocketClient.connect();

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSocketClient != null) {
                    JSONObject parameters = new JSONObject();
                    parameters.put("message_type", "message");
                    parameters.put("receiver", "1_1");
                    parameters.put("sender", "1_2");
                    parameters.put("message_random", getRandomFileName());
                    parameters.put("message", "123456");
                    parameters.put("type", "0");
                    mSocketClient.send(parameters.toJSONString());
                }
            }
        });
    }
    public static String getRandomFileName() {
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        Random random = new Random();
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        return rannum + str;// 当前时间


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSocketClient != null) {
            mSocketClient.close();
        }
    }
}
