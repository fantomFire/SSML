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
import zhonghuass.ssml.mvp.contract.MyCollectionContract;
import zhonghuass.ssml.mvp.model.appbean.CollectionBean;

import java.util.List;


@ActivityScope
public class MyCollectionModel extends BaseModel implements MyCollectionContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MyCollectionModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<CollectionBean>>> getMyCollection(String mId, String mType, int page) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getMyCollection(mId, mType, page);
    }

    @Override
    public Observable<BaseResponse<Void>> toConcern(String mId, String mType, String member_id, String member_type) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).toConcern(mId, mType, member_id, member_type);
    }

    @Override
    public Observable<BaseResponse<Void>> toCancelCollection(String mId, String mType, String content_id) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).toCancelCollection(mId, mType, content_id);
    }
}