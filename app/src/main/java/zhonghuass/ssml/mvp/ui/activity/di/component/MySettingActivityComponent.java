package zhonghuass.ssml.mvp.ui.activity.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.mvp.ui.activity.MySettingActivity;
import zhonghuass.ssml.mvp.ui.activity.di.module.MySettingActivityModule;

import com.jess.arms.di.scope.ActivityScope;

@ActivityScope
@Component(modules = MySettingActivityModule.class, dependencies = AppComponent.class)
public interface MySettingActivityComponent {
    void inject(MySettingActivity activity);
}