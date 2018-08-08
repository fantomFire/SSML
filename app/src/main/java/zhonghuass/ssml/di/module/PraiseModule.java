package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.PraiseContract;
import zhonghuass.ssml.mvp.model.PraiseModel;


@Module
public class PraiseModule {
    private PraiseContract.View view;

    /**
     * 构建PraiseModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PraiseModule(PraiseContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PraiseContract.View providePraiseView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PraiseContract.Model providePraiseModel(PraiseModel model) {
        return model;
    }
}