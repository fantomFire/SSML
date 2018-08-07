package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.CompanyRecommendContract;
import zhonghuass.ssml.mvp.model.CompanyRecommendModel;


@Module
public class CompanyRecommendModule {
    private CompanyRecommendContract.View view;

    /**
     * 构建CompanyRecommendModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CompanyRecommendModule(CompanyRecommendContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    CompanyRecommendContract.View provideCompanyRecommendView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    CompanyRecommendContract.Model provideCompanyRecommendModel(CompanyRecommendModel model) {
        return model;
    }
}