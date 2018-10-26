package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.ImageLayoutQY3Contract;
import zhonghuass.ssml.mvp.model.ImageLayoutQY3Model;


@Module
public class ImageLayoutQY3Module {
    private ImageLayoutQY3Contract.View view;

    /**
     * 构建ImageLayoutQY3Module时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ImageLayoutQY3Module(ImageLayoutQY3Contract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    ImageLayoutQY3Contract.View provideImageLayoutQY3View() {
        return this.view;
    }

    @FragmentScope
    @Provides
    ImageLayoutQY3Contract.Model provideImageLayoutQY3Model(ImageLayoutQY3Model model) {
        return model;
    }
}