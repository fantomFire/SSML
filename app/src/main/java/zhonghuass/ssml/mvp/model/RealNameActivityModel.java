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
import zhonghuass.ssml.mvp.contract.RealNameActivityContract;


@ActivityScope
public class RealNameActivityModel extends BaseModel implements RealNameActivityContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public RealNameActivityModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<Void>> postUserInfo(String user_id, String memberType, String eName, String eTel, String eCid) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class)
                .postUserInfo(user_id,memberType,eName,eTel,eCid)
                ;
    }
}