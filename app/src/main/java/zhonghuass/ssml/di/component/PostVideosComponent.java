package zhonghuass.ssml.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import zhonghuass.ssml.di.module.PostVideosModule;
import zhonghuass.ssml.mvp.ui.activity.PostVideosActivity;

@ActivityScope
@Component(modules = PostVideosModule.class, dependencies = AppComponent.class)
public interface PostVideosComponent {
    void inject(PostVideosActivity activity);
}