package zhonghuass.ssml.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import zhonghuass.ssml.mvp.contract.HowToRealNameActivityContract;


@ActivityScope
public class HowToRealNameActivityModel extends BaseModel implements HowToRealNameActivityContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public HowToRealNameActivityModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}