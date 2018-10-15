package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.SelectMBContract;
import zhonghuass.ssml.mvp.model.SelectMBModel;


@Module
public class SelectMBModule {
    private SelectMBContract.View view;

    /**
     * 构建SelectMBModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SelectMBModule(SelectMBContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SelectMBContract.View provideSelectMBView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SelectMBContract.Model provideSelectMBModel(SelectMBModel model) {
        return model;
    }
}