package zhonghuass.ssml.di.component;

import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.ImageLayoutQY2Module;

import com.jess.arms.di.scope.FragmentScope;
import zhonghuass.ssml.mvp.ui.fragment.ImageLayoutQY2Fragment;

@FragmentScope
@Component(modules = ImageLayoutQY2Module.class, dependencies = AppComponent.class)
public interface ImageLayoutQY2Component {
    void inject(ImageLayoutQY2Fragment fragment);
}