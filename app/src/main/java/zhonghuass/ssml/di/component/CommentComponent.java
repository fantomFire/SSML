package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.CommentModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.CommentActivity;

@ActivityScope
@Component(modules = CommentModule.class, dependencies = AppComponent.class)
public interface CommentComponent {
    void inject(CommentActivity activity);
}