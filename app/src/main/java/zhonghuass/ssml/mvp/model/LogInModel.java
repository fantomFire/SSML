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
import zhonghuass.ssml.mvp.contract.LogInContract;
import zhonghuass.ssml.mvp.model.appbean.LoginBean;


@ActivityScope
public class LogInModel extends BaseModel implements LogInContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LogInModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<LoginBean> toLogin(String mPhone, String mCode) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class)
                .toLogin(mPhone, mCode, "2");
    }

    @Override
    public Observable<BaseResponse<Void>> getCode(String mPhone) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class)
                .getCode(mPhone, "2");
    }
}