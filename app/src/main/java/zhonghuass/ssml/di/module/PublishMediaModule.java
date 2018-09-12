package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.PublishMediaContract;
import zhonghuass.ssml.mvp.model.PublishMediaModel;


@Module
public class PublishMediaModule {
    private PublishMediaContract.View view;

    /**
     * 构建PublishMediaModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PublishMediaModule(PublishMediaContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PublishMediaContract.View providePublishMediaView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PublishMediaContract.Model providePublishMediaModel(PublishMediaModel model) {
        return model;
    }
}