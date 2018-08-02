package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.HelpActivityContract;
import zhonghuass.ssml.mvp.model.HelpActivityModel;


@Module
public class HelpActivityModule {
    private HelpActivityContract.View view;

    /**
     * 构建HelpActivityModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public HelpActivityModule(HelpActivityContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    HelpActivityContract.View provideHelpActivityView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    HelpActivityContract.Model provideHelpActivityModel(HelpActivityModel model) {
        return model;
    }
}