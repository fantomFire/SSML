package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.MyConcernContract;
import zhonghuass.ssml.mvp.model.MyConcernModel;


@Module
public class MyConcernModule {
    private MyConcernContract.View view;

    /**
     * 构建MyConcernModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MyConcernModule(MyConcernContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyConcernContract.View provideMyConcernView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyConcernContract.Model provideMyConcernModel(MyConcernModel model) {
        return model;
    }
}