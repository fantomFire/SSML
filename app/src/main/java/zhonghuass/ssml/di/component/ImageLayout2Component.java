package zhonghuass.ssml.di.component;

import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.ImageLayout2Module;

import com.jess.arms.di.scope.FragmentScope;
import zhonghuass.ssml.mvp.ui.fragment.ImageLayout2Fragment;

@FragmentScope
@Component(modules = ImageLayout2Module.class, dependencies = AppComponent.class)
public interface ImageLayout2Component {
    void inject(ImageLayout2Fragment fragment);
}