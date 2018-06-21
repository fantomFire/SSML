package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.HomeFragmentContract;
import zhonghuass.ssml.mvp.model.HomeFragmentModel;


@Module
public class HomeFragmentModule {
    private HomeFragmentContract.View view;

    /**
     * 构建HomeFragmentModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public HomeFragmentModule(HomeFragmentContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    HomeFragmentContract.View provideHomeFragmentView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    HomeFragmentContract.Model provideHomeFragmentModel(HomeFragmentModel model) {
        return model;
    }
}