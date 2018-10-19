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
import zhonghuass.ssml.mvp.contract.VideoDetailContract;
import zhonghuass.ssml.mvp.model.appbean.DiscussBean;
import zhonghuass.ssml.mvp.model.appbean.GraphicBean;
import zhonghuass.ssml.utils.RxUtils;


@ActivityScope
public class VideoDetailPresenter extends BasePresenter<VideoDetailContract.Model, VideoDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public VideoDetailPresenter(VideoDetailContract.Model model, VideoDetailContract.View rootView) {
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

    public void vedioData(String content_id, String member_id, String member_type) {
        mModel.getVedioData(content_id,member_id,member_type)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<GraphicBean>(mErrorHandler) {
                    @Override
                    public void onNext(GraphicBean graphicBean) {
                        if(graphicBean.getStatus().equals("200")){
                            mRootView.showVeidoData(graphicBean.getData());
                        }else {
                            mRootView.showMessage(graphicBean.getMsg());
                        }
                    }
                });
    }

    public void addFocus(String user_id, String user_type, String member_id, String member_type) {
        mModel.addFocus(user_id,user_type,member_id,member_type)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Void>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Void> voidBaseResponse) {
                    mRootView.showMessage(voidBaseResponse.getMessage());
                    }
                });
    }

    public void addLike(String user_id, String content_id, String user_type) {
        mModel.addLike(user_id,content_id,user_type)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Void>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Void> voidBaseResponse) {
                        mRootView.showMessage(voidBaseResponse.getMessage());
                    }
                });
    }

    public void addCollect(String user_id, String content_id, String user_type) {
        mModel.addCollect(user_id,content_id,user_type)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Void>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Void> voidBaseResponse) {
                        mRootView.showMessage(voidBaseResponse.getMessage());
                    }
                });
    }

    public void getDiscussList(String content_id, String member_id, String member_type, int page) {
        mModel.getDiscussList(content_id, member_id, member_type,page)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<DiscussBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<DiscussBean>>  discussBeanBaseResponse) {
                        if (discussBeanBaseResponse.isSuccess()) {
                            mRootView.showDiscussData(discussBeanBaseResponse.getData());
                        } else if(discussBeanBaseResponse.getStatus().equals("201")) {
                            mRootView.notifystate();

                        }else {
                            mRootView.showMessage(discussBeanBaseResponse.getMessage());
                        }

                    }
                });
    }

    public void publishContext(String user_id, String member_type, String content_id, String mContext) {
        mModel.addPublish(user_id,member_type,content_id,mContext)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Void>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Void> voidBaseResponse) {
                        if(voidBaseResponse.isSuccess()){
                            mRootView.showPopState();
                        }
                        System.out.println("评论结果"+voidBaseResponse.getStatus()+voidBaseResponse.getMessage());
                        mRootView.showMessage(voidBaseResponse.getMessage());
                    }
                });
    }

    public void addContentLike(String user_id, String user_type, String comment_id, int position) {
        mModel.addContentLike(user_id,user_type,comment_id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Void>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Void> voidBaseResponse) {
                    if(voidBaseResponse.isSuccess()){
                            mRootView.ContentState(position);
                        }
                        mRootView.showMessage(voidBaseResponse.getMessage());
                    }
                });
    }
}
