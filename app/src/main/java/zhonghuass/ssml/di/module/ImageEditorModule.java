package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.ImageEditorContract;
import zhonghuass.ssml.mvp.model.ImageEditorModel;


@Module
public class ImageEditorModule {
    private ImageEditorContract.View view;

    /**
     * 构建ImageEditorModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ImageEditorModule(ImageEditorContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ImageEditorContract.View provideImageEditorView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ImageEditorContract.Model provideImageEditorModel(ImageEditorModel model) {
        return model;
    }
}