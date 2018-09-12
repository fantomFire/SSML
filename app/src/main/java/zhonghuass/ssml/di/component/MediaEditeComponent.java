package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.MediaEditeModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.MediaEditeActivity;

@ActivityScope
@Component(modules = MediaEditeModule.class, dependencies = AppComponent.class)
public interface MediaEditeComponent {
    void inject(MediaEditeActivity activity);
}