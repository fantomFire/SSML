package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.PicEditActivityContract;
import zhonghuass.ssml.mvp.model.PicEditActivityModel;


@Module
public class PicEditActivityModule {
    private PicEditActivityContract.View view;

    /**
     * 构建PicEditActivityModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PicEditActivityModule(PicEditActivityContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PicEditActivityContract.View providePicEditActivityView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PicEditActivityContract.Model providePicEditActivityModel(PicEditActivityModel model) {
        return model;
    }
}