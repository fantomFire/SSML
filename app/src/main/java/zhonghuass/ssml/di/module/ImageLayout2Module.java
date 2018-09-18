package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.ImageLayout2Contract;
import zhonghuass.ssml.mvp.model.ImageLayout2Model;


@Module
public class ImageLayout2Module {
    private ImageLayout2Contract.View view;

    /**
     * 构建ImageLayout2Module时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ImageLayout2Module(ImageLayout2Contract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    ImageLayout2Contract.View provideImageLayout2View() {
        return this.view;
    }

    @FragmentScope
    @Provides
    ImageLayout2Contract.Model provideImageLayout2Model(ImageLayout2Model model) {
        return model;
    }
}