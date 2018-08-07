package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.CompanyDanymicModule;

import com.jess.arms.di.scope.FragmentScope;

import zhonghuass.ssml.mvp.ui.fragment.CompanyDanymicFragment;

@FragmentScope
@Component(modules = CompanyDanymicModule.class, dependencies = AppComponent.class)
public interface CompanyDanymicComponent {
    void inject(CompanyDanymicFragment fragment);
}