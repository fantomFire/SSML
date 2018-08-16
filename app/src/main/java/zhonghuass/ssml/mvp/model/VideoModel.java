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
import zhonghuass.ssml.mvp.contract.VideoContract;
import zhonghuass.ssml.mvp.model.appbean.PhotoBean;


@FragmentScope
public class VideoModel extends BaseModel implements VideoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public VideoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<PhotoBean>>> getPhotoData(String eid, String target_type, String content_type, String member_id, String member_type, int page) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getPhotoData(eid,target_type,content_type,member_id,member_type,page+"");
    }
}