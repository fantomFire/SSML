package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.SystemContract;
import zhonghuass.ssml.mvp.model.SystemModel;


@Module
public class SystemModule {
    private SystemContract.View view;

    /**
     * 构建SystemModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SystemModule(SystemContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    SystemContract.View provideSystemView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    SystemContract.Model provideSystemModel(SystemModel model) {
        return model;
    }
}