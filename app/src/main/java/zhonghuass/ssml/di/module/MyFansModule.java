package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.MyFansContract;
import zhonghuass.ssml.mvp.model.MyFansModel;


@Module
public class MyFansModule {
    private MyFansContract.View view;

    /**
     * 构建MyFansModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MyFansModule(MyFansContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyFansContract.View provideMyFansView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyFansContract.Model provideMyFansModel(MyFansModel model) {
        return model;
    }
}