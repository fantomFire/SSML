package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.ConfirModiModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.ConfirModiActivity;

@ActivityScope
@Component(modules = ConfirModiModule.class, dependencies = AppComponent.class)
public interface ConfirModiComponent {
    void inject(ConfirModiActivity activity);
}