package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.LogInModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.LogInActivity;

@ActivityScope
@Component(modules = LogInModule.class, dependencies = AppComponent.class)
public interface LogInComponent {
    void inject(LogInActivity activity);
}