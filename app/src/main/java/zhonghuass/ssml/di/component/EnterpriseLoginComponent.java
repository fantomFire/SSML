package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.EnterpriseLoginModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.EnterpriseLoginActivity;

@ActivityScope
@Component(modules = EnterpriseLoginModule.class, dependencies = AppComponent.class)
public interface EnterpriseLoginComponent {
    void inject(EnterpriseLoginActivity activity);
}