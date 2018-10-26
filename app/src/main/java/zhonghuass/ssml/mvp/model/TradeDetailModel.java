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
import zhonghuass.ssml.mvp.contract.TradeDetailContract;


@ActivityScope
public class TradeDetailModel extends BaseModel implements TradeDetailContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public TradeDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<Void>> addFocus(String user_id, String user_type, String eid, String member_type) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class)
                .toConcern(user_id, user_type, eid, member_type);
    }
}