package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.EnterpriseLoginContract;
import zhonghuass.ssml.mvp.model.EnterpriseLoginModel;


@Module
public class EnterpriseLoginModule {
    private EnterpriseLoginContract.View view;

    /**
     * 构建EnterpriseLoginModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public EnterpriseLoginModule(EnterpriseLoginContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    EnterpriseLoginContract.View provideEnterpriseLoginView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    EnterpriseLoginContract.Model provideEnterpriseLoginModel(EnterpriseLoginModel model) {
        return model;
    }
}