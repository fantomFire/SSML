package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.ConcernContract;
import zhonghuass.ssml.mvp.model.ConcernModel;


@Module
public class ConcernModule {
    private ConcernContract.View view;

    /**
     * 构建ConcernModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ConcernModule(ConcernContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ConcernContract.View provideConcernView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ConcernContract.Model provideConcernModel(ConcernModel model) {
        return model;
    }
}