package zhonghuass.ssml.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import zhonghuass.ssml.http.ApiServer;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.CompanyContract;
import zhonghuass.ssml.mvp.model.appbean.AreaBean;
import zhonghuass.ssml.mvp.model.appbean.TradeBean;
import zhonghuass.ssml.mvp.model.appbean.TradeItemBean;


@FragmentScope
public class CompanyModel extends BaseModel implements CompanyContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CompanyModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<TradeBean>>> getTradeData(String area, String type, int currentPage, int pagesize) {

        return mRepositoryManager.obtainRetrofitService(ApiServer.class)
                .getTradeData(area,type,currentPage+"",pagesize+"");
    }

    @Override
    public Observable<ResponseBody> getAreaData() {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class)
                .getAreaData()
                ;
    }

    @Override
    public Observable<BaseResponse<List<TradeItemBean>>> getTradeItem() {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class)
                .getTradeItem()
                ;
    }
}