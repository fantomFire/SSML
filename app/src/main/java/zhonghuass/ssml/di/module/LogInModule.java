package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.LogInContract;
import zhonghuass.ssml.mvp.model.LogInModel;


@Module
public class LogInModule {
    private LogInContract.View view;

    /**
     * 构建LogInModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public LogInModule(LogInContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    LogInContract.View provideLogInView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    LogInContract.Model provideLogInModel(LogInModel model) {
        return model;
    }
}