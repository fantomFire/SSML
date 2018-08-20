package zhonghuass.ssml.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.CompanyRecommendContract;
import zhonghuass.ssml.mvp.model.appbean.ComanyrfBean;
import zhonghuass.ssml.utils.RxUtils;


@FragmentScope
public class CompanyRecommendPresenter extends BasePresenter<CompanyRecommendContract.Model, CompanyRecommendContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public CompanyRecommendPresenter(CompanyRecommendContract.Model model, CompanyRecommendContract.View rootView) {
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

    public void getcomanyrfData(String ep_id, int page, int pagesize) {
        mModel.getcomanyrfData(ep_id,page,pagesize)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<ComanyrfBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<ComanyrfBean> listBaseResponse) {
                        if (listBaseResponse.isSuccess()) {
                            mRootView.showComanyData(listBaseResponse);
                        } else if (listBaseResponse.getStatus().equals("201")) {
                            mRootView.addshowData();
                        } else {
                            mRootView.showMessage(listBaseResponse.getMessage());
                        }
                    }

                });
    }
}
