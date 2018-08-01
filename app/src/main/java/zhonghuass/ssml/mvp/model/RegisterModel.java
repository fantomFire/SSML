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
import zhonghuass.ssml.mvp.contract.RegisterContract;


@ActivityScope
public class RegisterModel extends BaseModel implements RegisterContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public RegisterModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<Void>> toRegist(String mPhone, String mPass, String mCode) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class)
                .toRegist(mPhone,mPass,mCode);
    }

    @Override
    public Observable<BaseResponse<Void>> toGetCode(String mPhone) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class)
                .getCode(mPhone,"1");
    }
}