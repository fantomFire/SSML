package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.TradeDetailContract;
import zhonghuass.ssml.mvp.model.TradeDetailModel;


@Module
public class TradeDetailModule {
    private TradeDetailContract.View view;

    /**
     * 构建TradeDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public TradeDetailModule(TradeDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    TradeDetailContract.View provideTradeDetailView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    TradeDetailContract.Model provideTradeDetailModel(TradeDetailModel model) {
        return model;
    }
}