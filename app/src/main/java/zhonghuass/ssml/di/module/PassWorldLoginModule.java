package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.PassWorldLoginContract;
import zhonghuass.ssml.mvp.model.PassWorldLoginModel;


@Module
public class PassWorldLoginModule {
    private PassWorldLoginContract.View view;

    /**
     * 构建PassWorldLoginModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PassWorldLoginModule(PassWorldLoginContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PassWorldLoginContract.View providePassWorldLoginView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PassWorldLoginContract.Model providePassWorldLoginModel(PassWorldLoginModel model) {
        return model;
    }
}