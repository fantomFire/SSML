package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.FocusModule;

import com.jess.arms.di.scope.FragmentScope;

import zhonghuass.ssml.mvp.ui.fragment.FocusFragment;

@FragmentScope
@Component(modules = FocusModule.class, dependencies = AppComponent.class)
public interface FocusComponent {
    void inject(FocusFragment fragment);
}