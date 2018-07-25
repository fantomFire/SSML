package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.MSMQContract;
import zhonghuass.ssml.mvp.model.MSMQModel;


@Module
public class MSMQModule {
    private MSMQContract.View view;

    /**
     * 构建MSMQModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MSMQModule(MSMQContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MSMQContract.View provideMSMQView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MSMQContract.Model provideMSMQModel(MSMQModel model) {
        return model;
    }
}