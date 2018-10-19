package zhonghuass.ssml.mvp.model;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.MySettingActivityContract;
import zhonghuass.ssml.mvp.model.MySettingActivityModel;


@Module
public class MySettingActivityModule {
    private MySettingActivityContract.View view;

    /**
     * 构建MySettingActivityModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MySettingActivityModule(MySettingActivityContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MySettingActivityContract.View provideMySettingActivityView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MySettingActivityContract.Model provideMySettingActivityModel(MySettingActivityModel model) {
        return model;
    }
}