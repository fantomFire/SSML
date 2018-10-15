package zhonghuass.ssml.di.component;

import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.SelectMBModule;

import com.jess.arms.di.scope.ActivityScope;
import zhonghuass.ssml.mvp.ui.activity.SelectMBActivity;

@ActivityScope
@Component(modules = SelectMBModule.class, dependencies = AppComponent.class)
public interface SelectMBComponent {
    void inject(SelectMBActivity activity);
}