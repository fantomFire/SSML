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
import zhonghuass.ssml.mvp.contract.ForgetPassworldContract;


@ActivityScope
public class ForgetPassworldModel extends BaseModel implements ForgetPassworldContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ForgetPassworldModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<Void>> toForgetPassworldVerification(String mPhone, String mCode) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).toForgetPassworldVerification(mPhone, mCode);
    }

    @Override
    public Observable<BaseResponse<Void>> getCode(String mPhone) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getCode(mPhone,"2");
    }
}