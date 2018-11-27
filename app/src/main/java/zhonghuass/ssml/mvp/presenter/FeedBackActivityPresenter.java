package zhonghuass.ssml.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.FeedBackActivityContract;
import zhonghuass.ssml.utils.RxUtils;


@ActivityScope
public class FeedBackActivityPresenter extends BasePresenter<FeedBackActivityContract.Model, FeedBackActivityContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public FeedBackActivityPresenter(FeedBackActivityContract.Model model, FeedBackActivityContract.View rootView) {
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

    public void postMess(String user_id, String memberType, String eContext) {
        mModel.postMess(user_id,memberType,eContext)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Void>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Void> voidBaseResponse) {
                        mRootView.showMessage(voidBaseResponse.getMessage());
                        System.out.println("意见"+voidBaseResponse.getMessage());
                        if(voidBaseResponse.getStatus().equals("200")){
                            mRootView.clearText();

                        }
                    }
                });


    }
}
