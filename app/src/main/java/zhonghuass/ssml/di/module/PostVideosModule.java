package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.PostVideosContract;
import zhonghuass.ssml.mvp.model.PostVideosModel;


@Module
public class PostVideosModule {
    private PostVideosContract.View view;

    /**
     * 构建PostVideosModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PostVideosModule(PostVideosContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PostVideosContract.View providePostVideosView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PostVideosContract.Model providePostVideosModel(PostVideosModel model) {
        return model;
    }
}