package zhonghuass.ssml.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import io.reactivex.Observable;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.model.appbean.DiscussBean;
import zhonghuass.ssml.mvp.model.appbean.GraphicBean;


public interface GraphicDetailsContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void showGraphicData(GraphicBean.DataBean data);

        void showDiscussData(List<DiscussBean> data);

        void changeFocusState();

        void changeLikeState();

        void showPopState();

        void notifystate();

        void ContentState(int position);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<GraphicBean> getGraphicData(String content_id, String member_id, String member_type);

        Observable<BaseResponse<List<DiscussBean>>> getDiscussList(String content_id, String member_id, String member_type, int page);

        Observable<BaseResponse<Void>> addFocus(String user_id, String content_id, String user_type, String member_type);

        Observable<BaseResponse<Void>> addLike(String user_id, String content_id, String user_type);

        Observable<BaseResponse<Void>> addCollect(String user_id, String content_id, String user_type);

        Observable<BaseResponse<Void>> addPublish(String user_id, String member_type, String content_id, String mContext);

        Observable<BaseResponse<Void>> addContentLike(String user_id, String user_type, String comment_id);
    }
}
