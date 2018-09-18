package zhonghuass.ssml.di.component;

import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.ImageLayout1Module;

import com.jess.arms.di.scope.FragmentScope;
import zhonghuass.ssml.mvp.ui.fragment.ImageLayout1Fragment;

@FragmentScope
@Component(modules = ImageLayout1Module.class, dependencies = AppComponent.class)
public interface ImageLayout1Component {
    void inject(ImageLayout1Fragment fragment);
}