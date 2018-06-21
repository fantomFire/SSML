package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.DialyModule;

import com.jess.arms.di.scope.FragmentScope;

import zhonghuass.ssml.mvp.ui.fragment.DialyFragment;

@FragmentScope
@Component(modules = DialyModule.class, dependencies = AppComponent.class)
public interface DialyComponent {
    void inject(DialyFragment fragment);
}