package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.HomeFragmentModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.fragment.HomeFragment;

@ActivityScope
@Component(modules = HomeFragmentModule.class, dependencies = AppComponent.class)
public interface HomeFragmentComponent {
    void inject(HomeFragment fragment);
}