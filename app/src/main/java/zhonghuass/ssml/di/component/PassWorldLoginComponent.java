package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.PassWorldLoginModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.PassWorldLoginActivity;

@ActivityScope
@Component(modules = PassWorldLoginModule.class, dependencies = AppComponent.class)
public interface PassWorldLoginComponent {
    void inject(PassWorldLoginActivity activity);
}