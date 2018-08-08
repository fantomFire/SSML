package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.RealNameActivityModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.RealNameActivity;

@ActivityScope
@Component(modules = RealNameActivityModule.class, dependencies = AppComponent.class)
public interface RealNameActivityComponent {
    void inject(RealNameActivity activity);
}