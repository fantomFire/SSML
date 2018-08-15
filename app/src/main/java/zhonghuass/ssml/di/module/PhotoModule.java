package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.PhotoContract;
import zhonghuass.ssml.mvp.model.PhotoModel;


@Module
public class PhotoModule {
    private PhotoContract.View view;

    /**
     * 构建PhotoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PhotoModule(PhotoContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    PhotoContract.View providePhotoView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    PhotoContract.Model providePhotoModel(PhotoModel model) {
        return model;
    }
}