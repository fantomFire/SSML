package zhonghuass.ssml.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import zhonghuass.ssml.di.module.TradeDetailModule;

import com.jess.arms.di.scope.ActivityScope;

import zhonghuass.ssml.mvp.ui.activity.TradeDetailActivity;

@ActivityScope
@Component(modules = TradeDetailModule.class, dependencies = AppComponent.class)
public interface TradeDetailComponent {
    void inject(TradeDetailActivity activity);
}