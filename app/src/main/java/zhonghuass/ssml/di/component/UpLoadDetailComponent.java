package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.UpLoadDetailModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.UpLoadDetailActivity;

@ActivityScope
@Component(modules = UpLoadDetailModule.class, dependencies = AppComponent.class)
public interface UpLoadDetailComponent {
    void inject(UpLoadDetailActivity activity);
}