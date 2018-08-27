package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.ImageEditorModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.ImageEditorActivity;

@ActivityScope
@Component(modules = ImageEditorModule.class, dependencies = AppComponent.class)
public interface ImageEditorComponent {
    void inject(ImageEditorActivity activity);
}