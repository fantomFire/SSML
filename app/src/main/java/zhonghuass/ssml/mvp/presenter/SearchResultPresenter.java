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
import zhonghuass.ssml.mvp.contract.SearchResultContract;
import zhonghuass.ssml.mvp.model.appbean.RecommendBean;
import zhonghuass.ssml.utils.RxUtils;


@ActivityScope
public class SearchResultPresenter extends BasePresenter<SearchResultContract.Model, SearchResultContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public SearchResultPresenter(SearchResultContract.Model model, SearchResultContract.View rootView) {
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

    public void getSearchResult(String member_id, String member_type, int page) {
        mModel.getSearchResult(member_id,member_type,page)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<RecommendBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<RecommendBean>> listBaseResponse) {
                        if (listBaseResponse.isSuccess()) {
                            mRootView.setContent(listBaseResponse.getData());
                        } else if (listBaseResponse.getStatus().equals("201")) {
                            mRootView.notifystate();
                        } else {
                            mRootView.showMessage(listBaseResponse.getMessage());
                        }
                    }
                });

    }
}
