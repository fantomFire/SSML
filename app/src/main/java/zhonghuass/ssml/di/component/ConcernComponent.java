package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.ConcernModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.ConcernActivity;

@ActivityScope
@Component(modules = ConcernModule.class, dependencies = AppComponent.class)
public interface ConcernComponent {
    void inject(ConcernActivity activity);
}