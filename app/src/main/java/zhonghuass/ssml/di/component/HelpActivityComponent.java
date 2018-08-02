package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.HelpActivityModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.HelpActivityActivity;

@ActivityScope
@Component(modules = HelpActivityModule.class, dependencies = AppComponent.class)
public interface HelpActivityComponent {
    void inject(HelpActivityActivity activity);
}