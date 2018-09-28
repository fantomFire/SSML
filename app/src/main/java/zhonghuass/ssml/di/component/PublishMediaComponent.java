package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.PublishMediaModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.PublishMediaActivity;

@ActivityScope
@Component(modules = PublishMediaModule.class, dependencies = AppComponent.class)
public interface PublishMediaComponent {
    void inject(PublishMediaActivity activity);
}