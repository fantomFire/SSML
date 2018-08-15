package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.VideoContract;
import zhonghuass.ssml.mvp.model.VideoModel;


@Module
public class VideoModule {
    private VideoContract.View view;

    /**
     * 构建VideoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public VideoModule(VideoContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    VideoContract.View provideVideoView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    VideoContract.Model provideVideoModel(VideoModel model) {
        return model;
    }
}