package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.ImageLayout1Contract;
import zhonghuass.ssml.mvp.model.ImageLayout1Model;


@Module
public class ImageLayout1Module {
    private ImageLayout1Contract.View view;

    /**
     * 构建ImageLayout1Module时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ImageLayout1Module(ImageLayout1Contract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    ImageLayout1Contract.View provideImageLayout1View() {
        return this.view;
    }

    @FragmentScope
    @Provides
    ImageLayout1Contract.Model provideImageLayout1Model(ImageLayout1Model model) {
        return model;
    }
}