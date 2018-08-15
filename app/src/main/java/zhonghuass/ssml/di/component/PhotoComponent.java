package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.PhotoModule;

import com.jess.arms.di.scope.FragmentScope;

import zhonghuass.ssml.mvp.ui.fragment.PhotoFragment;

@FragmentScope
@Component(modules = PhotoModule.class, dependencies = AppComponent.class)
public interface PhotoComponent {
    void inject(PhotoFragment fragment);
}