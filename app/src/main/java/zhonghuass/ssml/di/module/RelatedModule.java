package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.RelatedContract;
import zhonghuass.ssml.mvp.model.RelatedModel;


@Module
public class RelatedModule {
    private RelatedContract.View view;

    /**
     * 构建RelatedModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public RelatedModule(RelatedContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    RelatedContract.View provideRelatedView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    RelatedContract.Model provideRelatedModel(RelatedModel model) {
        return model;
    }
}