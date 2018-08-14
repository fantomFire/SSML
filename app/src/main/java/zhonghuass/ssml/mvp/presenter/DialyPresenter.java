package zhonghuass.ssml.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.DialyContract;
import zhonghuass.ssml.mvp.model.appbean.CommentBean;
import zhonghuass.ssml.mvp.model.appbean.DailyBean;
import zhonghuass.ssml.utils.RxUtils;


@FragmentScope
public class DialyPresenter extends BasePresenter<DialyContract.Model, DialyContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public DialyPresenter(DialyContract.Model model, DialyContract.View rootView) {
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

    public void getDailyData() {
        mModel.getDailyData()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<DailyBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<DailyBean>> listBaseResponse) {
                        if (listBaseResponse.isSuccess()) {
                            mRootView.showDailyData(listBaseResponse.getData());
                        }  else {
                            mRootView.showMessage(listBaseResponse.getMessage());
                        }
                    }
                });


    }
}
