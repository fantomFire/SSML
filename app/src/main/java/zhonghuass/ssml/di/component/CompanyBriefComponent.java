package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.CompanyBriefModule;

import com.jess.arms.di.scope.FragmentScope;

import zhonghuass.ssml.mvp.ui.fragment.CompanyBriefFragment;

@FragmentScope
@Component(modules = CompanyBriefModule.class, dependencies = AppComponent.class)
public interface CompanyBriefComponent {
    void inject(CompanyBriefFragment fragment);
}