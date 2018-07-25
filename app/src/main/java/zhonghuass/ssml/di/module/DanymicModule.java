package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.DanymicContract;
import zhonghuass.ssml.mvp.model.DanymicModel;


@Module
public class DanymicModule {
    private DanymicContract.View view;

    /**
     * 构建DanymicModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public DanymicModule(DanymicContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    DanymicContract.View provideDanymicView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    DanymicContract.Model provideDanymicModel(DanymicModel model) {
        return model;
    }
}