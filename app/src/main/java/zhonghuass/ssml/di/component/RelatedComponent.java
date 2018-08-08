package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.RelatedModule;

import com.jess.arms.di.scope.FragmentScope;

import zhonghuass.ssml.mvp.ui.fragment.RelatedFragment;

@FragmentScope
@Component(modules = RelatedModule.class, dependencies = AppComponent.class)
public interface RelatedComponent {
    void inject(RelatedFragment fragment);
}