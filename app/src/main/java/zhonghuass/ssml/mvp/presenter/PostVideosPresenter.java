package zhonghuass.ssml.mvp.presenter;

import android.app.Application;
import android.graphics.Bitmap;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import zhonghuass.ssml.http.BaseResponse;
import zhonghuass.ssml.mvp.contract.PostVideosContract;
import zhonghuass.ssml.mvp.model.appbean.IniviteBean;
import zhonghuass.ssml.utils.RxUtils;


@ActivityScope
public class PostVideosPresenter extends BasePresenter<PostVideosContract.Model, PostVideosContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public PostVideosPresenter(PostVideosContract.Model model, PostVideosContract.View rootView) {
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

    public void getInviteData(String ep_id, int page, int pagesize) {
        mModel.getInviteData(ep_id, page, pagesize)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<IniviteBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<IniviteBean> iniviteBeanBaseResponse) {
                        if (iniviteBeanBaseResponse.isSuccess()) {
                            mRootView.showdata(iniviteBeanBaseResponse);
                        } else if (iniviteBeanBaseResponse.getStatus().equals("201")) {
//                            mRootView.showdatatoast();
                        } else {
                            mRootView.showMessage(iniviteBeanBaseResponse.getMessage());
                        }
                    }
                });
    }

    public void upLoadData(List<String> paths, String mContent, String userEare, String dailyTag, String imagePath) {
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("content_type", convertToRequestBody("1"));
        map.put("member_id", convertToRequestBody("1"));
        map.put("member_type", convertToRequestBody("1"));
        map.put("content_title", convertToRequestBody("jfasdlkfadsfadskfjasdoi"));
        map.put("content_category", convertToRequestBody("1"));
        map.put("content_theme", convertToRequestBody("1"));
        map.put("content_position", convertToRequestBody("西安"));
        map.put("content_detail", convertToRequestBody("西安"));
        MultipartBody.Part[] parts = new MultipartBody.Part[paths.size()];
        final File file = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("uploadfile", file.getName(), requestFile);
        parts[0] = filePart;
/*
        MultipartBody.Part[] parts = new MultipartBody.Part[paths.size()];
        int cnt = 0;
        for (String imgPath : paths) {
            final File file = new File(imgPath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("uploadfile"+cnt, file.getName(), requestFile);
            parts[cnt] = filePart;
            cnt++;
        }*/
        mModel.upLoadData(map,parts)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Void>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Void> voidBaseResponse) {
                        System.out.println("voidBaseResponse"+voidBaseResponse.getStatus());
                    }
                });

    }
    private RequestBody convertToRequestBody(String param) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);
        return requestBody;
    }

    public void upImages(ArrayList<LocalMedia> paths, String content, String mContent, String userEare, String userId) {
        System.out.println("userId"+userId);
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("content_type", convertToRequestBody("1"));
        map.put("member_id", convertToRequestBody("1"));
        map.put("member_type", convertToRequestBody("1"));
        map.put("content_title", convertToRequestBody("jfasdlkfadsfadskfjasdoi"));
        map.put("content_category", convertToRequestBody("1"));
        map.put("content_theme", convertToRequestBody("0"));
        map.put("content_position", convertToRequestBody("西安"));
        map.put("content_detail", convertToRequestBody("西安"));
        paths.remove(paths.size()-1);
        MultipartBody.Part[] parts = new MultipartBody.Part[paths.size()];
        int cnt = 0;
        for (LocalMedia imgPath : paths) {
            System.out.println("===="+imgPath.getPath());
            final File file = new File(imgPath.getPath());
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("uploadfile[]", file.getName(), requestFile);
            parts[cnt] = filePart;
            cnt++;
        }
        mModel.upLoadData(map,parts)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Void>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Void> voidBaseResponse) {
                        System.out.println("voidBaseResponse"+voidBaseResponse.getMessage());
                        System.out.println("voidBaseResponse"+voidBaseResponse.getStatus());
                        mRootView.showMessage(voidBaseResponse.getMessage());
                    }
                });
    }
}
