package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.SearchResultModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.SearchResultActivity;

@ActivityScope
@Component(modules = SearchResultModule.class, dependencies = AppComponent.class)
public interface SearchResultComponent {
    void inject(SearchResultActivity activity);
}