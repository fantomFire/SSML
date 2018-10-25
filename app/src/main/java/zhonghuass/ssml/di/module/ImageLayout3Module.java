package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.ImageLayout3Contract;
import zhonghuass.ssml.mvp.model.ImageLayout3Model;


@Module
public class ImageLayout3Module {
    private ImageLayout3Contract.View view;

    /**
     * 构建ImageLayout3Module时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ImageLayout3Module(ImageLayout3Contract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    ImageLayout3Contract.View provideImageLayout3View() {
        return this.view;
    }

    @FragmentScope
    @Provides
    ImageLayout3Contract.Model provideImageLayout3Model(ImageLayout3Model model) {
        return model;
    }
}