package zhonghuass.ssml.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import zhonghuass.ssml.http.ApiServer;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.PostVideosContract;
import zhonghuass.ssml.mvp.model.appbean.IniviteBean;


@ActivityScope
public class PostVideosModel extends BaseModel implements PostVideosContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public PostVideosModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<IniviteBean>> getInviteData(String ep_id, int page, int pagesize) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getInviteData(ep_id,page,pagesize);
    }
}