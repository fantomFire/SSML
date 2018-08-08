package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.PicEditActivityModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.PicEditActivity;

@ActivityScope
@Component(modules = PicEditActivityModule.class, dependencies = AppComponent.class)
public interface PicEditActivityComponent {
    void inject(PicEditActivity activity);
}