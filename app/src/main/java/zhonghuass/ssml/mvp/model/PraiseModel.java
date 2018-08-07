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
import zhonghuass.ssml.mvp.contract.PraiseContract;
import zhonghuass.ssml.mvp.model.appbean.ShareMeBean;


@ActivityScope
public class PraiseModel extends BaseModel implements PraiseContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public PraiseModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<ShareMeBean>>> getPraiseData(String member_id, String member_type, int page) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class)
                .getConcernData(member_id,member_type,page+"");
    }
}