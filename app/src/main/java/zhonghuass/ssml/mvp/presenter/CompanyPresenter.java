package zhonghuass.ssml.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.CompanyContract;
import zhonghuass.ssml.mvp.model.appbean.TradeBean;
import zhonghuass.ssml.utils.RxUtils;


@FragmentScope
public class CompanyPresenter extends BasePresenter<CompanyContract.Model, CompanyContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public CompanyPresenter(CompanyContract.Model model, CompanyContract.View rootView) {
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

    public void getTradeData(String area, String type, int currentPage, int pagesize) {
        mModel.getTradeData(area,type,currentPage,pagesize)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<TradeBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<TradeBean>> listBaseResponse) {
                        if (listBaseResponse.isSuccess()) {
                            mRootView.showTradeData(listBaseResponse.getData());

                        } else {
                            mRootView.showMessage(listBaseResponse.getMessage());
                        }
                    }
                });


    }
}
