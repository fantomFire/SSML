package zhonghuass.ssml.di.component;

import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.PicEditModule;

import com.jess.arms.di.scope.ActivityScope;
import zhonghuass.ssml.mvp.ui.activity.PicEditActivity;

@ActivityScope
@Component(modules = PicEditModule.class, dependencies = AppComponent.class)
public interface PicEditComponent {
    void inject(PicEditActivity activity);
}