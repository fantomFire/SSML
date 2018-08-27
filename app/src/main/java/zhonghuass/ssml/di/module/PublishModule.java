package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.PublishContract;
import zhonghuass.ssml.mvp.model.PublishModel;


@Module
public class PublishModule {
    private PublishContract.View view;

    /**
     * 构建PublishModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PublishModule(PublishContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PublishContract.View providePublishView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PublishContract.Model providePublishModel(PublishModel model) {
        return model;
    }
}