package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.GraphicDetailsContract;
import zhonghuass.ssml.mvp.model.GraphicDetailsModel;


@Module
public class GraphicDetailsModule {
    private GraphicDetailsContract.View view;

    /**
     * 构建GraphicDetailsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public GraphicDetailsModule(GraphicDetailsContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    GraphicDetailsContract.View provideGraphicDetailsView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    GraphicDetailsContract.Model provideGraphicDetailsModel(GraphicDetailsModel model) {
        return model;
    }
}