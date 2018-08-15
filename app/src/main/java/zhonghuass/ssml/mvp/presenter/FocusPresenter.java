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
import zhonghuass.ssml.mvp.contract.FocusContract;
import zhonghuass.ssml.mvp.model.appbean.FocusBean;
import zhonghuass.ssml.utils.RxUtils;


@FragmentScope
public class FocusPresenter extends BasePresenter<FocusContract.Model, FocusContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public FocusPresenter(FocusContract.Model model, FocusContract.View rootView) {
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

    public void getFocusData(String member_id, String member_type, int page) {
        mModel.getFocusData(member_id,member_type,page)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<FocusBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<FocusBean>> focusBeanBaseResponse) {
                        if(focusBeanBaseResponse.isSuccess()){

                            mRootView.setContent(focusBeanBaseResponse.getData());
                        }else if (focusBeanBaseResponse.getStatus().equals("201")) {
                            mRootView.notifystate();
                        }else {
                            mRootView.showMessage(focusBeanBaseResponse.getMessage());
                        }

                    }
                });
    }
}
