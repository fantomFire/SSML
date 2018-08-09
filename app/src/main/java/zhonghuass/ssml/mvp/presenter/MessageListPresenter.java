package zhonghuass.ssml.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.MessageListContract;
import zhonghuass.ssml.mvp.model.appbean.MessageListBean;
import zhonghuass.ssml.mvp.model.appbean.ShareMeBean;
import zhonghuass.ssml.utils.RxUtils;


@ActivityScope
public class MessageListPresenter extends BasePresenter<MessageListContract.Model, MessageListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MessageListPresenter(MessageListContract.Model model, MessageListContract.View rootView) {
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

    public void getMessageListData(String member_id, String member_type, int page) {
        mModel.getMessageListData(member_id,member_type,page)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<MessageListBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<MessageListBean>> listBaseResponse) {
                        if (listBaseResponse.isSuccess()) {
                            mRootView.showMessageListData(listBaseResponse.getData());
                        } else if (listBaseResponse.getStatus().equals("201")) {
                            List<MessageListBean> arrayList=new ArrayList();
                            mRootView.showMessageListData(arrayList);
                        } else {
                            mRootView.showMessage(listBaseResponse.getMessage());
                        }
                    }
                });
    }
}
