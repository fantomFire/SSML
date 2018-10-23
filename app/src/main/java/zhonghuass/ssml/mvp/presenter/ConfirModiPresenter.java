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
import zhonghuass.ssml.mvp.contract.ConfirModiContract;
import zhonghuass.ssml.utils.RxUtils;


@ActivityScope
public class ConfirModiPresenter extends BasePresenter<ConfirModiContract.Model, ConfirModiContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ConfirModiPresenter(ConfirModiContract.Model model, ConfirModiContract.View rootView) {
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

    public void toConfirModi(String phone, String code, String newpw, String oldpw) {
        mModel.toConfirModi(phone, code, newpw,oldpw)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Void>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Void> voidBaseResponse) {
                        mRootView.showMessage(voidBaseResponse.getMessage());
                        if (voidBaseResponse.getStatus().equals("200")){
                            mRootView.toNewActivity();
                        }
                    }
                });
    }
}
