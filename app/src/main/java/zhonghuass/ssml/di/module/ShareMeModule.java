package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.ShareMeContract;
import zhonghuass.ssml.mvp.model.ShareMeModel;


@Module
public class ShareMeModule {
    private ShareMeContract.View view;

    /**
     * 构建ShareMeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ShareMeModule(ShareMeContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ShareMeContract.View provideShareMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ShareMeContract.Model provideShareMeModel(ShareMeModel model) {
        return model;
    }
}