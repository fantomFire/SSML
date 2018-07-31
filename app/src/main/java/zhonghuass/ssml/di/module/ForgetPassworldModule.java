package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.ForgetPassworldContract;
import zhonghuass.ssml.mvp.model.ForgetPassworldModel;


@Module
public class ForgetPassworldModule {
    private ForgetPassworldContract.View view;

    /**
     * 构建ForgetPassworldModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ForgetPassworldModule(ForgetPassworldContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ForgetPassworldContract.View provideForgetPassworldView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ForgetPassworldContract.Model provideForgetPassworldModel(ForgetPassworldModel model) {
        return model;
    }
}