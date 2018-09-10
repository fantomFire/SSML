package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.MediaPlayerModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.MediaPlayerActivity;

@ActivityScope
@Component(modules = MediaPlayerModule.class, dependencies = AppComponent.class)
public interface MediaPlayerComponent {
    void inject(MediaPlayerActivity activity);
}