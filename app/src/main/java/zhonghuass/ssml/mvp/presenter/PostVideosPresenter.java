package zhonghuass.ssml.mvp.presenter;

import android.app.Application;

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
import zhonghuass.ssml.mvp.model.appbean.RecomDetailBean;
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

    public void getInviteData() {
        mModel.getInviteData()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<RecomDetailBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<RecomDetailBean>> recomDetail) {
                        if (recomDetail.isSuccess()) {
                            mRootView.showdata(recomDetail.getData());
                        }
                    }

                });
    }

    public void upLoadData(String mediaPath, String mContent, String theme_id, String userId, String member_type, String imagePath, String mediaLength, String localtion) {
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("content_type", convertToRequestBody("1"));
        map.put("member_id", convertToRequestBody(userId));
        map.put("member_type", convertToRequestBody(member_type));
        map.put("content_theme", convertToRequestBody(theme_id));
        map.put("content_position", convertToRequestBody(localtion));
        map.put("content_title", convertToRequestBody(mContent));
        map.put("content_detail", convertToRequestBody(mediaLength));


        //封面
        final File file = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part coverPart = MultipartBody.Part.createFormData("image1", file.getName(), requestFile);

        //视频
        final File mediaFile = new File(mediaPath);
        RequestBody mediaBody = RequestBody.create(MediaType.parse("multipart/form-data"), mediaFile);
        MultipartBody.Part mediaPart = MultipartBody.Part.createFormData("video", mediaFile.getName(), mediaBody);

        System.out.println("视频大小"+mediaFile.length());
        mModel.upLoadData(map, coverPart, mediaPart)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Void>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Void> voidBaseResponse) {
                        if(voidBaseResponse.isSuccess()){
                            mRootView.closeActivity();
                        }
                        System.out.println("voidBaseResponse" + voidBaseResponse.getStatus());
                        mRootView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        t.printStackTrace();
                    }
                });

    }

    public void upImages(ArrayList<LocalMedia> paths, String content, String theme_id, String userId, String member_type, String localtion) {
        System.out.println("userId" + userId);
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("content_type", convertToRequestBody("0"));
        map.put("member_id", convertToRequestBody(userId));
        map.put("member_type", convertToRequestBody(member_type));
        map.put("content_theme", convertToRequestBody(theme_id));
        map.put("content_position", convertToRequestBody(localtion));
        map.put("content_detail", convertToRequestBody(content));
        //详情图片
        MultipartBody.Part[] parts = new MultipartBody.Part[paths.size()];
        int cnt = 0;
        for (LocalMedia localMedia : paths) {
            final File imgFile = new File(localMedia.getPath());
            RequestBody upLoadFile = RequestBody.create(MediaType.parse("multipart/form-data"), imgFile);
            MultipartBody.Part imagesPart = MultipartBody.Part.createFormData("image" + (cnt+1), imgFile.getName(), upLoadFile);
            parts[cnt] = imagesPart;
            cnt++;
        }


        mModel.upLoadImages(map,parts)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Void>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Void> voidBaseResponse) {
                        if(voidBaseResponse.isSuccess()){
                            mRootView.closeActivity();
                        }
                        System.out.println("voidBaseResponse" + voidBaseResponse.getMessage());
                        mRootView.showMessage(voidBaseResponse.getMessage());
                        mRootView.hideLoading();
                    }
                });
    }

    private RequestBody convertToRequestBody(String param) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);
        return requestBody;
    }

}
