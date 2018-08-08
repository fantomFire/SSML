package zhonghuass.ssml.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import zhonghuass.ssml.http.ApiServer;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.MessageListContract;
import zhonghuass.ssml.mvp.model.appbean.MessageListBean;


@ActivityScope
public class MessageListModel extends BaseModel implements MessageListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MessageListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<MessageListBean>>> getMessageListData(String member_id, String member_type, int page) {
        return  mRepositoryManager.obtainRetrofitService(ApiServer.class)
                .getMessageListData(member_id,member_type,page+"");
    }
}