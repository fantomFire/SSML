package zhonghuass.ssml.utils;

import org.greenrobot.eventbus.EventBus;

public class EventBusUtils {
    //注册事件
    public static void register(Object context) {
        if (!EventBus.getDefault().isRegistered(context)) {
            EventBus.getDefault().register(context);
        }
    }

    //解除
    public static void unregister(Object context) {
        if (EventBus.getDefault().isRegistered(context)) {
            EventBus.getDefault().unregister(context);
        }
    }

    //发送消息
    public static void post(Object object) {
        EventBus.getDefault().post(object);
    }

    //粘性消息(正常必须先注册，再发送才能收到，粘性消息可以解决先发送再注册问题)
    public static void postSticky(Object object) {
        EventBus.getDefault().postSticky(object);
    }
}
