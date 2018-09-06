package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.PicEditContract;
import zhonghuass.ssml.mvp.model.PicEditModel;


@Module
public class PicEditModule {
    private PicEditContract.View view;

    /**
     * 构建PicEditModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PicEditModule(PicEditContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PicEditContract.View providePicEditView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PicEditContract.Model providePicEditModel(PicEditModel model) {
        return model;
    }
}