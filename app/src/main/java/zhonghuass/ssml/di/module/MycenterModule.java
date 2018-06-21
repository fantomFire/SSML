package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.MycenterContract;
import zhonghuass.ssml.mvp.model.MycenterModel;


@Module
public class MycenterModule {
    private MycenterContract.View view;

    /**
     * 构建MycenterModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MycenterModule(MycenterContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    MycenterContract.View provideMycenterView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    MycenterContract.Model provideMycenterModel(MycenterModel model) {
        return model;
    }
}