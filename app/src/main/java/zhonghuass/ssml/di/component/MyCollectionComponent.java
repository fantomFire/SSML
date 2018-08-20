package zhonghuass.ssml.di.component;

import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.MyCollectionModule;

import com.jess.arms.di.scope.ActivityScope;
import zhonghuass.ssml.mvp.ui.activity.MyCollectionActivity;

@ActivityScope
@Component(modules = MyCollectionModule.class, dependencies = AppComponent.class)
public interface MyCollectionComponent {
    void inject(MyCollectionActivity activity);
}