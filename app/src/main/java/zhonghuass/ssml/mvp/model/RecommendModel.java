package zhonghuass.ssml.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import zhonghuass.ssml.http.ApiServer;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.RecommendContract;
import zhonghuass.ssml.mvp.model.appbean.RecommendBean;


@FragmentScope
public class RecommendModel extends BaseModel implements RecommendContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public RecommendModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<RecommendBean>>> getRecommendData(String member_id, String member_type, int page) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class)
                .getRecommendDatas(member_id,member_type,page+"")
                ;
    }
}