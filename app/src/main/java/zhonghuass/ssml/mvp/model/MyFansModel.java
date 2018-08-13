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
import zhonghuass.ssml.mvp.contract.MyFansContract;
import zhonghuass.ssml.mvp.model.appbean.ConcernFansBean;


@ActivityScope
public class MyFansModel extends BaseModel implements MyFansContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MyFansModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<ConcernFansBean>>> getMyFansData(String mId, String mType, int page) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getMyFansData(mId, mType, page);
    }

    @Override
    public Observable<BaseResponse<Void>> toCancelConcern(String mId, String mType, String member_id, String member_type) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).toCancelConcern(mId, mType, member_id, member_type);
    }

    @Override
    public Observable<BaseResponse<Void>> toConcern(String mId, String mType, String member_id, String member_type) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).toConcern(mId, mType, member_id, member_type);
    }
}