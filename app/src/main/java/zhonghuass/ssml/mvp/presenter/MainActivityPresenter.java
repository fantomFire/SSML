package zhonghuass.ssml.mvp.presenter;

import android.app.Application;

import android.util.Log;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.MainActivityContract;
import zhonghuass.ssml.mvp.model.appbean.LoginBean;
import zhonghuass.ssml.mvp.model.appbean.UserInfoBean;
import zhonghuass.ssml.mvp.ui.activity.MainActivity;
import zhonghuass.ssml.utils.ACache;
import zhonghuass.ssml.utils.Constants;
import zhonghuass.ssml.utils.RxUtils;


@ActivityScope
public class MainActivityPresenter extends BasePresenter<MainActivityContract.Model, MainActivityContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MainActivityPresenter(MainActivityContract.Model model, MainActivityContract.View rootView) {
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

    public void getMyStatistics(String member_id, String member_type) {
        mModel.getMyStatistics(member_id, member_type)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UserInfoBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<UserInfoBean> userInfoBeanBaseResponse) {
                        mRootView.getStatisticsSuccess(userInfoBeanBaseResponse.getData());
                    }
                });
    }
}
