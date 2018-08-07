package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.CompanyInviteModule;

import com.jess.arms.di.scope.FragmentScope;

import zhonghuass.ssml.mvp.ui.fragment.CompanyInviteFragment;

@FragmentScope
@Component(modules = CompanyInviteModule.class, dependencies = AppComponent.class)
public interface CompanyInviteComponent {
    void inject(CompanyInviteFragment fragment);
}