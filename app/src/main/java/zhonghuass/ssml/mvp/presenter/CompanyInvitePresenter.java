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
import zhonghuass.ssml.mvp.contract.CompanyInviteContract;
import zhonghuass.ssml.mvp.model.appbean.IniviteBean;
import zhonghuass.ssml.utils.RxUtils;


@FragmentScope
public class CompanyInvitePresenter extends BasePresenter<CompanyInviteContract.Model, CompanyInviteContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public CompanyInvitePresenter(CompanyInviteContract.Model model, CompanyInviteContract.View rootView) {
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
                            mRootView.showdatatoast();
                        } else {
                            mRootView.showMessage(iniviteBeanBaseResponse.getMessage());
                        }
                    }
                });
    }
}
