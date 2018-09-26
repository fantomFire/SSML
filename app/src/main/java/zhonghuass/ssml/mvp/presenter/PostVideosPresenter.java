package zhonghuass.ssml.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.PostVideosContract;
import zhonghuass.ssml.mvp.model.appbean.IniviteBean;
import zhonghuass.ssml.utils.RxUtils;


@ActivityScope
public class PostVideosPresenter extends BasePresenter<PostVideosContract.Model, PostVideosContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public PostVideosPresenter(PostVideosContract.Model model, PostVideosContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getInviteData(String ep_id, int page, int pagesize) {
        mModel.getInviteData(ep_id, page, pagesize)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<IniviteBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<IniviteBean> iniviteBeanBaseResponse) {
                        if (iniviteBeanBaseResponse.isSuccess()) {
                            mRootView.showdata(iniviteBeanBaseResponse);
                        } else if (iniviteBeanBaseResponse.getStatus().equals("201")) {
//                            mRootView.showdatatoast();
                        } else {
                            mRootView.showMessage(iniviteBeanBaseResponse.getMessage());
                        }
                    }
                });
    }

    public void upLoadData(List<String> paths, String mContent, String userEare, String dailyTag) {
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("uid", convertToRequestBody(mContent));
        map.put("truename", convertToRequestBody(userEare));
        map.put("identity_card", convertToRequestBody(dailyTag));
        MultipartBody.Part[] parts = new MultipartBody.Part[paths.size()];
        int cnt = 0;
        for (String imgPath : paths) {
            final File file = new File(imgPath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("uploadfile[]", file.getName(), requestFile);
            parts[cnt] = filePart;
            cnt++;
        }
      /*  mModel.upLoadData(map,parts)
                .c*/

    }
    private RequestBody convertToRequestBody(String param) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);
        return requestBody;
    }
}
