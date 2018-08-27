package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.PublishModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.PublishActivity;

@ActivityScope
@Component(modules = PublishModule.class, dependencies = AppComponent.class)
public interface PublishComponent {
    void inject(PublishActivity activity);
}