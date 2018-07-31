package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.ConfirModiContract;
import zhonghuass.ssml.mvp.model.ConfirModiModel;


@Module
public class ConfirModiModule {
    private ConfirModiContract.View view;

    /**
     * 构建ConfirModiModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ConfirModiModule(ConfirModiContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ConfirModiContract.View provideConfirModiView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ConfirModiContract.Model provideConfirModiModel(ConfirModiModel model) {
        return model;
    }
}