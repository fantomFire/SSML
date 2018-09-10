package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.MediaPlayerContract;
import zhonghuass.ssml.mvp.model.MediaPlayerModel;


@Module
public class MediaPlayerModule {
    private MediaPlayerContract.View view;

    /**
     * 构建MediaPlayerModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MediaPlayerModule(MediaPlayerContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MediaPlayerContract.View provideMediaPlayerView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MediaPlayerContract.Model provideMediaPlayerModel(MediaPlayerModel model) {
        return model;
    }
}