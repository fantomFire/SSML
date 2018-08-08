package zhonghuass.ssml.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.LogInContract;
import zhonghuass.ssml.mvp.model.appbean.LoginBean;
import zhonghuass.ssml.utils.RxUtils;


@ActivityScope
public class LogInPresenter extends BasePresenter<LogInContract.Model, LogInContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public LogInPresenter(LogInContract.Model model, LogInContract.View rootView) {
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

    public void toLogin(String mPhone, String mCode) {
        mModel.toLogin(mPhone,mCode)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<LoginBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<LoginBean> voidBaseResponse) {
                        mRootView.showMessage(voidBaseResponse.getMessage());
                        mRootView.showContent(voidBaseResponse.getMessage());
                    }
                });

    }

    public void getCode(String mPhone) {
        mModel.getCode(mPhone)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Void>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Void> loginBeanBaseResponse) {
                        mRootView.showContent(loginBeanBaseResponse.getMessage());
                    }
                });
    }
}
