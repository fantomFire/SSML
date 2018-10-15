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
import zhonghuass.ssml.mvp.contract.CompanyBriefContract;
import zhonghuass.ssml.mvp.model.appbean.BriefBean;
import zhonghuass.ssml.utils.RxUtils;


@FragmentScope
public class CompanyBriefPresenter extends BasePresenter<CompanyBriefContract.Model, CompanyBriefContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public CompanyBriefPresenter(CompanyBriefContract.Model model, CompanyBriefContract.View rootView) {
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

    public void getDetailData(String eid) {
        mModel.getDetailData(eid)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<BriefBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<BriefBean>> listBaseResponse) {
                        if(listBaseResponse.isSuccess()){
                            mRootView.setBriefData(listBaseResponse.getData().get(0));
                        }
                    }
                });
    }
}
