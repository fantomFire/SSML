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
import zhonghuass.ssml.mvp.contract.ForgetPassworldContract;
import zhonghuass.ssml.utils.RxUtils;


@ActivityScope
public class ForgetPassworldPresenter extends BasePresenter<ForgetPassworldContract.Model, ForgetPassworldContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ForgetPassworldPresenter(ForgetPassworldContract.Model model, ForgetPassworldContract.View rootView) {
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

    public void toForgetPassworldVerification(String mPhone, String mCode) {
        mModel.toForgetPassworldVerification(mPhone, mCode)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Void>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Void> voidBaseResponse) {
                        if (voidBaseResponse.getStatus().equals("200")) {
                            mRootView.toNewActivity();
                        } else {
                            mRootView.showMessage(voidBaseResponse.getMessage());
                        }
                    }
                });
    }

    public void togetCode(String mPhone) {
        mModel.getCode(mPhone)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Void>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Void> voidBaseResponse) {
                        mRootView.showMessage(voidBaseResponse.getMessage());
                    }
                });
    }
}
