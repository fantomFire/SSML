package zhonghuass.ssml.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import zhonghuass.ssml.di.module.MainActivityModule;
import zhonghuass.ssml.mvp.ui.activity.MainActivity;

@ActivityScope
@Component(modules = MainActivityModule.class, dependencies = AppComponent.class)
public interface MainActivityComponent {
    void inject(MainActivity activity);
}