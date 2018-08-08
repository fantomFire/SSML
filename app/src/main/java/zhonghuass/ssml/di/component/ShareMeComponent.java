package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.ShareMeModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.ShareMeActivity;

@ActivityScope
@Component(modules = ShareMeModule.class, dependencies = AppComponent.class)
public interface ShareMeComponent {
    void inject(ShareMeActivity activity);
}