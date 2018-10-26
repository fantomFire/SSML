package zhonghuass.ssml.di.component;

import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.ImageLayoutQY3Module;

import com.jess.arms.di.scope.FragmentScope;
import zhonghuass.ssml.mvp.ui.fragment.ImageLayoutQY3Fragment;

@FragmentScope
@Component(modules = ImageLayoutQY3Module.class, dependencies = AppComponent.class)
public interface ImageLayoutQY3Component {
    void inject(ImageLayoutQY3Fragment fragment);
}