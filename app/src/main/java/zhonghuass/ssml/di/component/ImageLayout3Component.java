package zhonghuass.ssml.di.component;

import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.ImageLayout3Module;

import com.jess.arms.di.scope.FragmentScope;
import zhonghuass.ssml.mvp.ui.fragment.ImageLayout3Fragment;

@FragmentScope
@Component(modules = ImageLayout3Module.class, dependencies = AppComponent.class)
public interface ImageLayout3Component {
    void inject(ImageLayout3Fragment fragment);
}