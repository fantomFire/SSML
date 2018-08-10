package zhonghuass.ssml.mvp.contract;


import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import io.reactivex.Observable;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.model.appbean.ConcernFansBean;


public interface MyConcernContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void showDate(List<ConcernFansBean> data);

        void showCancelSuccess(String message);

        void showConcernSuccess(String message);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<List<ConcernFansBean>>> getMyConcernData(String member_id, String member_type, int page);

        Observable<BaseResponse<Void>> toCancelConcern(String mId, String mType, String member_id, String member_type);

        Observable<BaseResponse<Void>> toConcern(String mId, String mType, String member_id, String member_type);
    }
}
