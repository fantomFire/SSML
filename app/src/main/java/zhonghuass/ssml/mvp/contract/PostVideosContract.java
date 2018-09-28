package zhonghuass.ssml.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.model.appbean.IniviteBean;


public interface PostVideosContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void showdata(BaseResponse<IniviteBean> iniviteBeanBaseResponse);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BaseResponse<IniviteBean>> getInviteData(String ep_id, int page, int pagesize);

        Observable <BaseResponse<Void>> upLoadData(HashMap<String, RequestBody> map, MultipartBody.Part[] parts);
    }
}
