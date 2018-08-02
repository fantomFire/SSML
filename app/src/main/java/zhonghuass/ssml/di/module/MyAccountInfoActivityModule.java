package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.MyAccountInfoActivityContract;
import zhonghuass.ssml.mvp.model.MyAccountInfoActivityModel;


@Module
public class MyAccountInfoActivityModule {
    private MyAccountInfoActivityContract.View view;

    /**
     * 构建MyAccountInfoActivityModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MyAccountInfoActivityModule(MyAccountInfoActivityContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyAccountInfoActivityContract.View provideMyAccountInfoActivityView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyAccountInfoActivityContract.Model provideMyAccountInfoActivityModel(MyAccountInfoActivityModel model) {
        return model;
    }
}