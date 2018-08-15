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
import zhonghuass.ssml.mvp.contract.DialyContract;
import zhonghuass.ssml.mvp.model.appbean.DailyBean;
import zhonghuass.ssml.mvp.model.appbean.DailyChoicenessBean;
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

    public void getDailyData(String member_id,String member_type,int page) {
        mModel.getDailyData(member_id, member_type, page)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<DailyChoicenessBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<DailyChoicenessBean>> listBaseResponse) {
                        if (listBaseResponse.isSuccess()) {
                            mRootView.showDailyData(listBaseResponse.getData());
                        } else if (listBaseResponse.getStatus().equals("201")) {
                            mRootView.notifystate();
                        }  else {
                            mRootView.showMessage(listBaseResponse.getMessage());
                        }
                    }
                });


    }

    public void getDailyHeaderData() {
        mModel.getDailyHeaderData()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<DailyBean>(mErrorHandler) {
                    @Override
                    public void onNext(DailyBean listBaseResponse) {
                        if (listBaseResponse.getStatus().equals("200")) {

                            mRootView.showDailyHeaderData(listBaseResponse);
                        } else {
                            mRootView.showMessage(listBaseResponse.getMsg());
                        }
                    }
                });
    }
}
