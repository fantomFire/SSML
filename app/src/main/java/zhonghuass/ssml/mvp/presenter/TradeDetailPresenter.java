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
import zhonghuass.ssml.mvp.contract.TradeDetailContract;
import zhonghuass.ssml.utils.RxUtils;


@ActivityScope
public class TradeDetailPresenter extends BasePresenter<TradeDetailContract.Model, TradeDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public TradeDetailPresenter(TradeDetailContract.Model model, TradeDetailContract.View rootView) {
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

    public void addFocus(String user_id, String user_type, String eid, String member_type) {
        mModel.addFocus(user_id,user_type,eid,member_type)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Void>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Void> voidBaseResponse) {

                        mRootView.showMessage(voidBaseResponse.getMessage());
                        if(voidBaseResponse.isSuccess()){
                            mRootView.changeFocusState();
                        }
                    }
                });
    }
}
