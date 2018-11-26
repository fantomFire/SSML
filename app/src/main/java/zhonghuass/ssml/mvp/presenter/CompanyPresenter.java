package zhonghuass.ssml.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.CompanyContract;
import zhonghuass.ssml.mvp.model.appbean.AreaBean;
import zhonghuass.ssml.mvp.model.appbean.TradeBean;
import zhonghuass.ssml.mvp.model.appbean.TradeItemBean;
import zhonghuass.ssml.utils.RxUtils;


@FragmentScope
public class CompanyPresenter extends BasePresenter<CompanyContract.Model, CompanyContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public CompanyPresenter(CompanyContract.Model model, CompanyContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getTradeData(String area, String type, int currentPage, int pagesize) {
        mModel.getTradeData(area,type,currentPage,pagesize)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<TradeBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<TradeBean>> listBaseResponse) {
                        System.out.println("stat"+listBaseResponse.getStatus());
                        if (listBaseResponse.isSuccess()) {
                            mRootView.showTradeData(listBaseResponse.getData());

                        }else if(listBaseResponse.getStatus().equals("201")){
                            mRootView.notifystate();
                        } else {
                            mRootView.showMessage(listBaseResponse.getMessage());
                        }
                    }
                });


    }

    public void getAreaData() {
        mModel.getAreaData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String mString = responseBody.string();
                            String data ="\"data\":";
                            int i = mString.indexOf(data);
                            String substring = mString.substring(i + data.length(), mString.length() - 1);
                            mRootView.showAreaData(substring);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                    e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                });



    }

    public void getTradeItem() {
        mModel.getTradeItem()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<TradeItemBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<TradeItemBean>> listBaseResponse) {
                    if(listBaseResponse.isSuccess()){
                        mRootView.showTradeItem(listBaseResponse.getData());

                    }
                    }
                });
    }
}
