package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.ChattingModule;
import zhonghuass.ssml.mvp.ui.activity.ChattingActivity;

import com.jess.arms.di.scope.ActivityScope;

@ActivityScope
@Component(modules = ChattingModule.class, dependencies = AppComponent.class)
public interface ChattingComponent {
    void inject(ChattingActivity activity);
}