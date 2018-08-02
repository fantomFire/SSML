package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.RealNameActivityContract;
import zhonghuass.ssml.mvp.model.RealNameActivityModel;


@Module
public class RealNameActivityModule {
    private RealNameActivityContract.View view;

    /**
     * 构建RealNameActivityModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public RealNameActivityModule(RealNameActivityContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RealNameActivityContract.View provideRealNameActivityView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    RealNameActivityContract.Model provideRealNameActivityModel(RealNameActivityModel model) {
        return model;
    }
}