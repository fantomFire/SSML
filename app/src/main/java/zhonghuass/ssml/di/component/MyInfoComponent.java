package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.MyInfoModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.MyInfoActivity;

@ActivityScope
@Component(modules = MyInfoModule.class, dependencies = AppComponent.class)
public interface MyInfoComponent {
    void inject(MyInfoActivity activity);
}