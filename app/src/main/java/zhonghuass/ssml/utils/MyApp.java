package zhonghuass.ssml.utils;

import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.bumptech.glide.request.target.ViewTarget;
import com.jess.arms.base.BaseApplication;
import com.zhangke.websocket.WebSocketService;
import com.zhangke.websocket.WebSocketSetting;

import zhonghuass.ssml.R;
import zhonghuass.ssml.utils.mywebsocket.AppResponseDispatcher;

public class MyApp  extends BaseApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        ViewTarget.setTagId(R.id.tag_glide);
        //配置 WebSocket，必须在 WebSocket 服务启动前设置
        WebSocketSetting.setConnectUrl("ws://video.zhonghuass.cn:10000");//必选
        WebSocketSetting.setResponseProcessDelivery(new AppResponseDispatcher());
        WebSocketSetting.setReconnectWithNetworkChanged(true);

        //启动 WebSocket 服务
        startService(new Intent(this, WebSocketService.class));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
