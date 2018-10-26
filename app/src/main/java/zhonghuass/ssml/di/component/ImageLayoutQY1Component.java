package zhonghuass.ssml.di.component;

import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.ImageLayoutQY1Module;

import com.jess.arms.di.scope.FragmentScope;
import zhonghuass.ssml.mvp.ui.fragment.ImageLayoutQY1Fragment;

@FragmentScope
@Component(modules = ImageLayoutQY1Module.class, dependencies = AppComponent.class)
public interface ImageLayoutQY1Component {
    void inject(ImageLayoutQY1Fragment fragment);
}