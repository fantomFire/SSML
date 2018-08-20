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
import zhonghuass.ssml.mvp.contract.MyCollectionContract;
import zhonghuass.ssml.mvp.model.appbean.CollectionBean;
import zhonghuass.ssml.mvp.model.appbean.ConcernFansBean;
import zhonghuass.ssml.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;


@ActivityScope
public class MyCollectionPresenter extends BasePresenter<MyCollectionContract.Model, MyCollectionContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MyCollectionPresenter(MyCollectionContract.Model model, MyCollectionContract.View rootView) {
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

    public void getMyCollection(String mId, String mType, int page) {
        mModel.getMyCollection(mId, mType, page)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<CollectionBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<CollectionBean>> listBaseResponse) {
                        if (listBaseResponse.isSuccess()) {
                            mRootView.showData(listBaseResponse.getData());
                        } else if (listBaseResponse.getStatus().equals("201")) {
                            mRootView.showNoData();
                        } else {
                            mRootView.showMessage(listBaseResponse.getMessage());
                        }
                    }
                });
    }

    public void toConcern(String mId, String mType, String member_id, String member_type) {
        mModel.toConcern(mId, mType, member_id, member_type)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.showConcernSuccess(baseResponse.getMessage());
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    public void toCancelCollection(String mId, String mType, String content_id) {
        mModel.toCancelCollection(mId, mType, content_id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.showCancelCollectionSuccess(baseResponse.getMessage());
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }
}
