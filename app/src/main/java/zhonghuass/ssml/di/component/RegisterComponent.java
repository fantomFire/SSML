package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.RegisterModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.RegisterActivity;

@ActivityScope
@Component(modules = RegisterModule.class, dependencies = AppComponent.class)
public interface RegisterComponent {
    void inject(RegisterActivity activity);
}