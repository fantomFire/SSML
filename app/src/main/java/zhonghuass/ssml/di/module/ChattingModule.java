package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.ChattingContract;
import zhonghuass.ssml.mvp.model.ChattingModel;


@Module
public class ChattingModule {
    private ChattingContract.View view;

    /**
     * 构建ChattingModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ChattingModule(ChattingContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ChattingContract.View provideChattingView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ChattingContract.Model provideChattingModel(ChattingModel model) {
        return model;
    }
}