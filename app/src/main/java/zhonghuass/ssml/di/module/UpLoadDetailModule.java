package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.UpLoadDetailContract;
import zhonghuass.ssml.mvp.model.UpLoadDetailModel;


@Module
public class UpLoadDetailModule {
    private UpLoadDetailContract.View view;

    /**
     * 构建UpLoadDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public UpLoadDetailModule(UpLoadDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    UpLoadDetailContract.View provideUpLoadDetailView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    UpLoadDetailContract.Model provideUpLoadDetailModel(UpLoadDetailModel model) {
        return model;
    }
}