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
import zhonghuass.ssml.mvp.contract.CompanyInviteContract;
import zhonghuass.ssml.mvp.model.appbean.IniviteBean;


@FragmentScope
public class CompanyInviteModel extends BaseModel implements CompanyInviteContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CompanyInviteModel(IRepositoryManager repositoryManager) {
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