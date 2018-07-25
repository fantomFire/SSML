package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.DanymicModule;

import com.jess.arms.di.scope.FragmentScope;

import zhonghuass.ssml.mvp.ui.fragment.DanymicFragment;

@FragmentScope
@Component(modules = DanymicModule.class, dependencies = AppComponent.class)
public interface DanymicComponent {
    void inject(DanymicFragment fragment);
}