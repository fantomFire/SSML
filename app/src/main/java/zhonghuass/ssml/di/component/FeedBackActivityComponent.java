package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.FeedBackActivityModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.FeedBackActivityActivity;

@ActivityScope
@Component(modules = FeedBackActivityModule.class, dependencies = AppComponent.class)
public interface FeedBackActivityComponent {
    void inject(FeedBackActivityActivity activity);
}