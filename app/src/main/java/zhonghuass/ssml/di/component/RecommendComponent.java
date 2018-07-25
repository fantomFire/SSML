package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.RecommendModule;

import com.jess.arms.di.scope.FragmentScope;

import zhonghuass.ssml.mvp.ui.fragment.RecommendFragment;

@FragmentScope
@Component(modules = RecommendModule.class, dependencies = AppComponent.class)
public interface RecommendComponent {
    void inject(RecommendFragment fragment);
}