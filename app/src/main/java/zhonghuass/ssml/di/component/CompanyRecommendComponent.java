package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.CompanyRecommendModule;

import com.jess.arms.di.scope.FragmentScope;

import zhonghuass.ssml.mvp.ui.fragment.CompanyRecommendFragment;

@FragmentScope
@Component(modules = CompanyRecommendModule.class, dependencies = AppComponent.class)
public interface CompanyRecommendComponent {
    void inject(CompanyRecommendFragment fragment);
}