package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.HowToRealNameActivityContract;
import zhonghuass.ssml.mvp.model.HowToRealNameActivityModel;


@Module
public class HowToRealNameActivityModule {
    private HowToRealNameActivityContract.View view;

    /**
     * 构建HowToRealNameActivityModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public HowToRealNameActivityModule(HowToRealNameActivityContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    HowToRealNameActivityContract.View provideHowToRealNameActivityView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    HowToRealNameActivityContract.Model provideHowToRealNameActivityModel(HowToRealNameActivityModel model) {
        return model;
    }
}