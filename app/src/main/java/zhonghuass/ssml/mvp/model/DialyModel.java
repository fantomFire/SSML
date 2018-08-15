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
import zhonghuass.ssml.mvp.contract.DialyContract;
import zhonghuass.ssml.mvp.model.appbean.DailyBean;
import zhonghuass.ssml.mvp.model.appbean.DailyChoicenessBean;


@FragmentScope
public class DialyModel extends BaseModel implements DialyContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public DialyModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<BaseResponse<List<DailyChoicenessBean>>> getDailyData(String member_id, String member_type, int page) {
        return  mRepositoryManager.obtainRetrofitService(ApiServer.class)
                .getDailyData(member_id,member_type,page+"");
    }

    @Override
    public Observable<DailyBean> getDailyHeaderData() {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getDailyHeaderData();
    }
}