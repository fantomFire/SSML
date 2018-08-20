package zhonghuass.ssml.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import io.reactivex.Observable;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.model.appbean.CollectionBean;
import zhonghuass.ssml.mvp.model.appbean.ConcernFansBean;

import java.util.List;


public interface MyCollectionContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void showData(List<CollectionBean> data);

        void showNoData();

        void showConcernSuccess(String message);

        void showCancelCollectionSuccess(String message);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BaseResponse<List<CollectionBean>>> getMyCollection(String mId, String mType, int page);

        Observable<BaseResponse<Void>> toConcern(String mId, String mType, String member_id, String member_type);

        Observable<BaseResponse<Void>> toCancelCollection(String mId, String mType, String content_id);
    }
}
