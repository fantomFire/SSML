package zhonghuass.ssml.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import zhonghuass.ssml.mvp.contract.EnterpriseLoginContract;
import zhonghuass.ssml.mvp.model.appbean.EPLoginBean;
import zhonghuass.ssml.utils.RxUtils;


@ActivityScope
public class EnterpriseLoginPresenter extends BasePresenter<EnterpriseLoginContract.Model, EnterpriseLoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public EnterpriseLoginPresenter(EnterpriseLoginContract.Model model, EnterpriseLoginContract.View rootView) {
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

    public void eptoLogin(String mPhone, String mPassworld) {
        mModel.eptoLogin(mPhone, mPassworld)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<EPLoginBean>(mErrorHandler) {
                    @Override
                    public void onNext(EPLoginBean voidBaseResponse) {
                        mRootView.showMessage(voidBaseResponse.msg);
                        if (voidBaseResponse.status.equals("200")){
                            mRootView.gotoActivity(voidBaseResponse);
                        }
                    }

                });
    }
}
