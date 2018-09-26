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
import zhonghuass.ssml.mvp.contract.GraphicDetailsContract;
import zhonghuass.ssml.mvp.model.appbean.GraphicBean;
import zhonghuass.ssml.utils.RxUtils;


@ActivityScope
public class GraphicDetailsPresenter extends BasePresenter<GraphicDetailsContract.Model, GraphicDetailsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public GraphicDetailsPresenter(GraphicDetailsContract.Model model, GraphicDetailsContract.View rootView) {
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

    public void getGraphicData(String content_id, String member_id, String member_type) {
        mModel.getGraphicData(content_id,  member_id,  member_type)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<GraphicBean>(mErrorHandler) {
                    @Override
                    public void onNext(GraphicBean listBaseResponse) {
                        System.out.println("结果"+ listBaseResponse.getStatus());
                      //  System.out.println("结果"+ listBaseResponse.getData().getContent_title());
                        String status = listBaseResponse.getStatus();

                        if (status.equals("200")) {

                            mRootView.showGraphicData(listBaseResponse.getData());
                        } else {

                            mRootView.showMessage(listBaseResponse.getMsg());
                        }
                    }
                });

    }
}
