package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.VideoModule;

import com.jess.arms.di.scope.FragmentScope;

import zhonghuass.ssml.mvp.ui.fragment.VideoFragment;

@FragmentScope
@Component(modules = VideoModule.class, dependencies = AppComponent.class)
public interface VideoComponent {
    void inject(VideoFragment fragment);
}