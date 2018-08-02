package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.MyAppUpdateModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.MyAppUpdateActivity;

@ActivityScope
@Component(modules = MyAppUpdateModule.class, dependencies = AppComponent.class)
public interface MyAppUpdateComponent {
    void inject(MyAppUpdateActivity activity);
}