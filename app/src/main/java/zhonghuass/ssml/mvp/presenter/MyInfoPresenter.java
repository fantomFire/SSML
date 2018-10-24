package zhonghuass.ssml.mvp.presenter;

import android.app.Application;

import android.util.Log;
import android.widget.Toast;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import com.luck.picture.lib.entity.LocalMedia;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.MyInfoContract;
import zhonghuass.ssml.mvp.model.appbean.UserInfoBean;
import zhonghuass.ssml.utils.RxUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@ActivityScope
public class MyInfoPresenter extends BasePresenter<MyInfoContract.Model, MyInfoContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MyInfoPresenter(MyInfoContract.Model model, MyInfoContract.View rootView) {
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

    public void updateInfo(List<LocalMedia> paths, String member_id, String nickname, String provincie, String city, String area, String introduction) {

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("member_id", convertToRequestBody(member_id));
        map.put("nickname", convertToRequestBody(nickname));
        map.put("provincie", convertToRequestBody(provincie));
        map.put("city", convertToRequestBody(city));
        map.put("area", convertToRequestBody(area));
        map.put("introduction", convertToRequestBody(introduction));
        map.put("status", convertToRequestBody("1"));
        MultipartBody.Part photoPart = null;

        if (paths != null && paths.size() > 0) {
            //头像
            final File file = new File(paths.get(0).getPath());
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            photoPart = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
        }


//        //详情图片
//        MultipartBody.Part[] parts = new MultipartBody.Part[paths.size()];
//        int cnt = 0;
//        for (LocalMedia localMedia : paths) {
//            final File imgFile = new File(localMedia.getPath());
//            RequestBody upLoadFile = RequestBody.create(MediaType.parse("multipart/form-data"), imgFile);
//            MultipartBody.Part imagesPart = MultipartBody.Part.createFormData("image" + cnt, file.getName(), upLoadFile);
//            parts[cnt] = imagesPart;
//            cnt++;
//        }


        mModel.updateInfo(map, photoPart)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<UserInfoBean>>>(mErrorHandler) {

                    @Override
                    public void onNext(BaseResponse<List<UserInfoBean>> userInfoBeans) {


                        Log.e("--","修改完毕。");
                        Log.e("--","状态。"+userInfoBeans.getStatus());
                        Log.e("--","消息。"+userInfoBeans.getMessage());
                        Log.e("--","头像。"+userInfoBeans.getData().get(0).avatar);
                        Log.e("--","用户信息。"+userInfoBeans.getData().get(0).toString());
                        mRootView.showUpdateSuccess(userInfoBeans.getData());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showError();
                        Log.e("--","修改问题："+t);
                    }
                });

    }

    private RequestBody convertToRequestBody(String param) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);
        return requestBody;
    }


}

