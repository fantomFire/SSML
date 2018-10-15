package zhonghuass.ssml.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import zhonghuass.ssml.http.ApiServer;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.PostVideosContract;
import zhonghuass.ssml.mvp.model.appbean.IniviteBean;
import zhonghuass.ssml.mvp.model.appbean.RecomDetailBean;


@ActivityScope
public class PostVideosModel extends BaseModel implements PostVideosContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public PostVideosModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<RecomDetailBean>>> getInviteData() {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getRecomDetail();
    }


    @Override
    public Observable<BaseResponse<Void>> upLoadImages(HashMap<String, RequestBody> map, MultipartBody.Part parts, MultipartBody.Part[] parts1) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class)
                .upLoadImages(map,parts,parts1);
    }

    @Override
    public Observable<BaseResponse<Void>> upLoadData(HashMap<String, RequestBody> map, MultipartBody.Part coverPart, MultipartBody.Part mediaPart) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class)
                .upLoadData(map,coverPart,mediaPart);
    }
}