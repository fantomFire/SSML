package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.MSMQModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.MSMQActivity;

@ActivityScope
@Component(modules = MSMQModule.class, dependencies = AppComponent.class)
public interface MSMQComponent {
    void inject(MSMQActivity activity);
}