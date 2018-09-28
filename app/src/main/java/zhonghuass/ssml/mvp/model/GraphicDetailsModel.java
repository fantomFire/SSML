package zhonghuass.ssml.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import zhonghuass.ssml.http.ApiServer;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.GraphicDetailsContract;
import zhonghuass.ssml.mvp.model.appbean.GraphicBean;


@ActivityScope
public class GraphicDetailsModel extends BaseModel implements GraphicDetailsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public GraphicDetailsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<GraphicBean> getGraphicData(String content_id, String member_id, String member_type) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getGraphicData(content_id,member_id,member_type);
    }
}