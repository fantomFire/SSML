package zhonghuass.ssml.mvp.ui.activity.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.mvp.ui.activity.di.module.MySettingActivityModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.MySettingActivityActivity;

@ActivityScope
@Component(modules = MySettingActivityModule.class, dependencies = AppComponent.class)
public interface MySettingActivityComponent {
    void inject(MySettingActivityActivity activity);
}