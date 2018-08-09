package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.MyFansModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.MyFansActivity;

@ActivityScope
@Component(modules = MyFansModule.class, dependencies = AppComponent.class)
public interface MyFansComponent {
    void inject(MyFansActivity activity);
}