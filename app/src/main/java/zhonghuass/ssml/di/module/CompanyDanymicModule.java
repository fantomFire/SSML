package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.CompanyDanymicContract;
import zhonghuass.ssml.mvp.model.CompanyDanymicModel;


@Module
public class CompanyDanymicModule {
    private CompanyDanymicContract.View view;

    /**
     * 构建CompanyDanymicModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CompanyDanymicModule(CompanyDanymicContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    CompanyDanymicContract.View provideCompanyDanymicView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    CompanyDanymicContract.Model provideCompanyDanymicModel(CompanyDanymicModel model) {
        return model;
    }
}