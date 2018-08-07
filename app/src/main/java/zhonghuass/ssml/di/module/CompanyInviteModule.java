package zhonghuass.ssml.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import zhonghuass.ssml.mvp.contract.CompanyInviteContract;
import zhonghuass.ssml.mvp.model.CompanyInviteModel;


@Module
public class CompanyInviteModule {
    private CompanyInviteContract.View view;

    /**
     * 构建CompanyInviteModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CompanyInviteModule(CompanyInviteContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    CompanyInviteContract.View provideCompanyInviteView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    CompanyInviteContract.Model provideCompanyInviteModel(CompanyInviteModel model) {
        return model;
    }
}