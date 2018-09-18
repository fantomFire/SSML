package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.WeiXinLoginModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.WeiXinLoginActivity;

@ActivityScope
@Component(modules = WeiXinLoginModule.class, dependencies = AppComponent.class)
public interface WeiXinLoginComponent {
    void inject(WeiXinLoginActivity activity);
}