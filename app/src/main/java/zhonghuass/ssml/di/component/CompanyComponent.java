package zhonghuass.ssml.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Component;
import zhonghuass.ssml.di.module.CompanyModule;
import zhonghuass.ssml.mvp.ui.fragment.CompanyFragment;

@FragmentScope
@Component(modules = CompanyModule.class, dependencies = AppComponent.class)
public interface CompanyComponent {
    void inject(CompanyFragment fragment);
}