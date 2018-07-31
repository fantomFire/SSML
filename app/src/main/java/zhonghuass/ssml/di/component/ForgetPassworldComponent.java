package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.ForgetPassworldModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.ForgetPassworldActivity;

@ActivityScope
@Component(modules = ForgetPassworldModule.class, dependencies = AppComponent.class)
public interface ForgetPassworldComponent {
    void inject(ForgetPassworldActivity activity);
}