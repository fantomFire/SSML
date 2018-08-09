package zhonghuass.ssml.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;
import java.util.function.DoubleUnaryOperator;

import io.reactivex.Observable;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.model.appbean.MessageListBean;
import zhonghuass.ssml.mvp.model.appbean.ShareMeBean;


public interface MessageListContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void showMessageListData(List<MessageListBean> data);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BaseResponse<List<MessageListBean>>> getMessageListData(String member_id, String member_type, int page);
    }
}
