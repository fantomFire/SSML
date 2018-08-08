package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.PraiseModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.PraiseActivity;

@ActivityScope
@Component(modules = PraiseModule.class, dependencies = AppComponent.class)
public interface PraiseComponent {
    void inject(PraiseActivity activity);
}