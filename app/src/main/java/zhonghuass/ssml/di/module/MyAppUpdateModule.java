package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.MyAppUpdateContract;
import zhonghuass.ssml.mvp.model.MyAppUpdateModel;


@Module
public class MyAppUpdateModule {
    private MyAppUpdateContract.View view;

    /**
     * 构建MyAppUpdateModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MyAppUpdateModule(MyAppUpdateContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyAppUpdateContract.View provideMyAppUpdateView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyAppUpdateContract.Model provideMyAppUpdateModel(MyAppUpdateModel model) {
        return model;
    }
}