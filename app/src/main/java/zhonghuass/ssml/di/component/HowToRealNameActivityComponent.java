package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.HowToRealNameActivityModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.HowToRealNameActivityActivity;

@ActivityScope
@Component(modules = HowToRealNameActivityModule.class, dependencies = AppComponent.class)
public interface HowToRealNameActivityComponent {
    void inject(HowToRealNameActivityActivity activity);
}