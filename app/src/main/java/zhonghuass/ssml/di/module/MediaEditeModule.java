package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.MediaEditeContract;
import zhonghuass.ssml.mvp.model.MediaEditeModel;


@Module
public class MediaEditeModule {
    private MediaEditeContract.View view;

    /**
     * 构建MediaEditeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MediaEditeModule(MediaEditeContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MediaEditeContract.View provideMediaEditeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MediaEditeContract.Model provideMediaEditeModel(MediaEditeModel model) {
        return model;
    }
}