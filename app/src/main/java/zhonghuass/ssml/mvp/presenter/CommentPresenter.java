package zhonghuass.ssml.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.CommentContract;
import zhonghuass.ssml.mvp.model.appbean.CommentBean;
import zhonghuass.ssml.utils.RxUtils;


@ActivityScope
public class CommentPresenter extends BasePresenter<CommentContract.Model, CommentContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public CommentPresenter(CommentContract.Model model, CommentContract.View rootView) {
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


    public void getCommentData(String member_id, String member_type, int page) {
        mModel.getCommentData(member_id, member_type, page)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<CommentBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<CommentBean>> listBaseResponse) {
                        if (listBaseResponse.isSuccess()) {
                            mRootView.showCommentData(listBaseResponse.getData());
                        } else if (listBaseResponse.getStatus().equals("201")) {
                            List<CommentBean> arrayList = new ArrayList();
                            mRootView.showCommentData(arrayList);
                        } else {
                            mRootView.showMessage(listBaseResponse.getMessage());
                        }
                    }
                });
    }
}
