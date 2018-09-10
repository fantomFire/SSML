package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.GraphicDetailsModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.GraphicDetailsActivity;

@ActivityScope
@Component(modules = GraphicDetailsModule.class, dependencies = AppComponent.class)
public interface GraphicDetailsComponent {
    void inject(GraphicDetailsActivity activity);
}