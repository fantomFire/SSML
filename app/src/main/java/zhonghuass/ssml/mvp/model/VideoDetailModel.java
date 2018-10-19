package zhonghuass.ssml.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import zhonghuass.ssml.http.ApiServer;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.VideoDetailContract;
import zhonghuass.ssml.mvp.model.appbean.DiscussBean;
import zhonghuass.ssml.mvp.model.appbean.GraphicBean;


@ActivityScope
public class VideoDetailModel extends BaseModel implements VideoDetailContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public VideoDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<GraphicBean> getVedioData(String content_id, String member_id, String member_type) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class)
                .getGraphicData(content_id, member_id, member_type)
                ;
    }

    @Override
    public Observable<BaseResponse<Void>> addFocus(String user_id, String user_type, String member_id, String member_type) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class)
                .toConcern(user_id, user_type, member_id, member_type);
    }

    @Override
    public Observable<BaseResponse<Void>> addLike(String user_id, String content_id, String user_type) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class)
                .addLike(user_id, content_id, user_type);
    }

    @Override
    public Observable<BaseResponse<Void>> addCollect(String user_id, String content_id, String user_type) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class)
                .addColect(user_id, content_id, user_type)
                ;
    }

    @Override
    public Observable<BaseResponse<List<DiscussBean>>> getDiscussList(String content_id, String member_id, String member_type, int page) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class)
                .getDiscussData(content_id, member_id, member_type, page + "")
                ;
    }

    @Override
    public Observable<BaseResponse<Void>> addPublish(String user_id, String member_type, String content_id, String mContext) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class)
                .addPublish(user_id, member_type, content_id, mContext)
                ;
    }

    @Override
    public Observable<BaseResponse<Void>> addContentLike(String user_id, String user_type, String comment_id) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class)
                .addContentZan(user_id,user_type,comment_id)
                ;
    }
}