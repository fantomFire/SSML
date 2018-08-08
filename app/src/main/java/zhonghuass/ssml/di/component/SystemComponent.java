package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.SystemModule;

import com.jess.arms.di.scope.FragmentScope;

import zhonghuass.ssml.mvp.ui.fragment.SystemFragment;

@FragmentScope
@Component(modules = SystemModule.class, dependencies = AppComponent.class)
public interface SystemComponent {
    void inject(SystemFragment fragment);
}