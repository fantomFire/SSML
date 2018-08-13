package zhonghuass.ssml.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import java.util.List;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.SearchContract;
import zhonghuass.ssml.mvp.model.appbean.HistoryBean;
import zhonghuass.ssml.mvp.model.appbean.RecommendBean;
import zhonghuass.ssml.utils.RxUtils;


@ActivityScope
public class SearchPresenter extends BasePresenter<SearchContract.Model, SearchContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public SearchPresenter(SearchContract.Model model, SearchContract.View rootView) {
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

    public void getSearchHistoryData(String member_id, String member_type) {
        mModel.getSearchHistoryData(member_id,member_type)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<HistoryBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<HistoryBean>> listBaseResponse) {
                        if (listBaseResponse.isSuccess()) {
                            mRootView.setSearchHistory(listBaseResponse.getData());
                        } else {
                            mRootView.showMessage(listBaseResponse.getMessage());
                        }
                    }
                });

    }
}
