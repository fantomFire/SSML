package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.CompanyContract;
import zhonghuass.ssml.mvp.model.CompanyModel;


@Module
public class CompanyModule {
    private CompanyContract.View view;

    /**
     * 构建CompanyModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CompanyModule(CompanyContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    CompanyContract.View provideCompanyView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    CompanyContract.Model provideCompanyModel(CompanyModel model) {
        return model;
    }
}