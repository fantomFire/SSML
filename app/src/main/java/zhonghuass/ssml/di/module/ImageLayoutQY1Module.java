package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.ImageLayoutQY1Contract;
import zhonghuass.ssml.mvp.model.ImageLayoutQY1Model;


@Module
public class ImageLayoutQY1Module {
    private ImageLayoutQY1Contract.View view;

    /**
     * 构建ImageLayoutQY1Module时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ImageLayoutQY1Module(ImageLayoutQY1Contract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    ImageLayoutQY1Contract.View provideImageLayoutQY1View() {
        return this.view;
    }

    @FragmentScope
    @Provides
    ImageLayoutQY1Contract.Model provideImageLayoutQY1Model(ImageLayoutQY1Model model) {
        return model;
    }
}