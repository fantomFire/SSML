package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.MyConcernModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.MyConcernActivity;

@ActivityScope
@Component(modules = MyConcernModule.class, dependencies = AppComponent.class)
public interface MyConcernComponent {
    void inject(MyConcernActivity activity);
}