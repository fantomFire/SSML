package zhonghuass.ssml.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.model.appbean.RecomDetailBean;


public interface PostVideosContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void showdata(List<RecomDetailBean> iniviteBeanBaseResponse);

        void closeActivity();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BaseResponse<List<RecomDetailBean>>> getInviteData();

        Observable <BaseResponse<Void>>  upLoadImages(HashMap<String, RequestBody> map, MultipartBody.Part[] parts1);

        Observable <BaseResponse<Void>>  upLoadData(HashMap<String, RequestBody> map, MultipartBody.Part coverPart, MultipartBody.Part mediaPart);
    }
}
