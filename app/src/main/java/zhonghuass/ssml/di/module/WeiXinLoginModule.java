package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.WeiXinLoginContract;
import zhonghuass.ssml.mvp.model.WeiXinLoginModel;


@Module
public class WeiXinLoginModule {
    private WeiXinLoginContract.View view;

    /**
     * 构建WeiXinLoginModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public WeiXinLoginModule(WeiXinLoginContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    WeiXinLoginContract.View provideWeiXinLoginView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    WeiXinLoginContract.Model provideWeiXinLoginModel(WeiXinLoginModel model) {
        return model;
    }
}