package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.MainActivityContract;
import zhonghuass.ssml.mvp.model.MainActivityModel;


@Module
public class MainActivityModule {
    private MainActivityContract.View view;

    /**
     * 构建MainActivityModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MainActivityModule(MainActivityContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MainActivityContract.View provideMainActivityView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MainActivityContract.Model provideMainActivityModel(MainActivityModel model) {
        return model;
    }
}