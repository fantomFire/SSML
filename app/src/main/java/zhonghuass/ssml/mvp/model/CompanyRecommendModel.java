package zhonghuass.ssml.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import zhonghuass.ssml.http.ApiServer;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.CompanyRecommendContract;
import zhonghuass.ssml.mvp.model.appbean.ComanyrfBean;


@FragmentScope
public class CompanyRecommendModel extends BaseModel implements CompanyRecommendContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CompanyRecommendModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<ComanyrfBean>> getcomanyrfData(String ep_id, int page,int pagesize) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getcomanyrfData(ep_id,page,pagesize);
    }
}