package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.MycenterModule;

import com.jess.arms.di.scope.FragmentScope;

import zhonghuass.ssml.mvp.ui.fragment.MycenterFragment;

@FragmentScope
@Component(modules = MycenterModule.class, dependencies = AppComponent.class)
public interface MycenterComponent {
    void inject(MycenterFragment fragment);
}