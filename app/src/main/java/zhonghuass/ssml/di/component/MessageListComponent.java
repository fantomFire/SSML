package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.MessageListModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.MessageListActivity;

@ActivityScope
@Component(modules = MessageListModule.class, dependencies = AppComponent.class)
public interface MessageListComponent {
    void inject(MessageListActivity activity);
}