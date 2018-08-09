package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.SearchModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.SearchActivity;

@ActivityScope
@Component(modules = SearchModule.class, dependencies = AppComponent.class)
public interface SearchComponent {
    void inject(SearchActivity activity);
}