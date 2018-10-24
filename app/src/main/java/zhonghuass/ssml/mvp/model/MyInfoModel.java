package zhonghuass.ssml.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import zhonghuass.ssml.http.ApiServer;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.MyInfoContract;
import zhonghuass.ssml.mvp.model.appbean.UserInfoBean;

import java.util.HashMap;
import java.util.List;


@ActivityScope
public class MyInfoModel extends BaseModel implements MyInfoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MyInfoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<UserInfoBean>>> updateInfo(HashMap<String, RequestBody> map, MultipartBody.Part parts) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).updateMyInfo(map,parts);

    }
}