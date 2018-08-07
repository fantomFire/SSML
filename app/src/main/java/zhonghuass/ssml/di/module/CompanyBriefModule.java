package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.CompanyBriefContract;
import zhonghuass.ssml.mvp.model.CompanyBriefModel;


@Module
public class CompanyBriefModule {
    private CompanyBriefContract.View view;

    /**
     * 构建CompanyBriefModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CompanyBriefModule(CompanyBriefContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    CompanyBriefContract.View provideCompanyBriefView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    CompanyBriefContract.Model provideCompanyBriefModel(CompanyBriefModel model) {
        return model;
    }
}