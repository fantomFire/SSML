package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.ImageLayoutQY2Contract;
import zhonghuass.ssml.mvp.model.ImageLayoutQY2Model;


@Module
public class ImageLayoutQY2Module {
    private ImageLayoutQY2Contract.View view;

    /**
     * 构建ImageLayoutQY2Module时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ImageLayoutQY2Module(ImageLayoutQY2Contract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    ImageLayoutQY2Contract.View provideImageLayoutQY2View() {
        return this.view;
    }

    @FragmentScope
    @Provides
    ImageLayoutQY2Contract.Model provideImageLayoutQY2Model(ImageLayoutQY2Model model) {
        return model;
    }
}