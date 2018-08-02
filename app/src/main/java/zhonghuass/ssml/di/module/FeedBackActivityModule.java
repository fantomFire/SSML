package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.FeedBackActivityContract;
import zhonghuass.ssml.mvp.model.FeedBackActivityModel;


@Module
public class FeedBackActivityModule {
    private FeedBackActivityContract.View view;

    /**
     * 构建FeedBackActivityModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public FeedBackActivityModule(FeedBackActivityContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FeedBackActivityContract.View provideFeedBackActivityView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    FeedBackActivityContract.Model provideFeedBackActivityModel(FeedBackActivityModel model) {
        return model;
    }
}