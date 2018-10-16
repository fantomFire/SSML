package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.mvp.model.VideoDetailModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.VideoDetailActivity;

@ActivityScope
@Component(modules = VideoDetailModule.class, dependencies = AppComponent.class)
public interface VideoDetailComponent {
    void inject(VideoDetailActivity activity);
}