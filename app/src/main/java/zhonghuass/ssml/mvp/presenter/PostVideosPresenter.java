package zhonghuass.ssml.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
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
}
