package zhonghuass.ssml.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import java.util.List;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.PhotoContract;
import zhonghuass.ssml.mvp.model.appbean.PhotoBean;
import zhonghuass.ssml.utils.RxUtils;


@FragmentScope
public class PhotoPresenter extends BasePresenter<PhotoContract.Model, PhotoContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public PhotoPresenter(PhotoContract.Model model, PhotoContract.View rootView) {
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

    public void getPhotoData(String eid, String target_type, String content_type, String member_id, String member_type, int page) {
        mModel.getPhotoData(eid,target_type,content_type,member_id,member_type,page)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<PhotoBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<PhotoBean>> listBaseResponse) {
                        System.out.println("~!~~~~~"+listBaseResponse.getStatus());
                        if(listBaseResponse.isSuccess()){

                            mRootView.setContent(listBaseResponse.getData());
                        }else if (listBaseResponse.getStatus().equals("201")) {
                            mRootView.notifystate();
                        }else {
                            mRootView.showMessage(listBaseResponse.getMessage());
                        }
                    }

                });

    }
}
