package zhonghuass.ssml.http;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import zhonghuass.ssml.mvp.model.appbean.BriefBean;
import zhonghuass.ssml.mvp.model.appbean.CollectionBean;
import zhonghuass.ssml.mvp.model.appbean.ComanyrfBean;
import zhonghuass.ssml.mvp.model.appbean.CommentBean;
import zhonghuass.ssml.mvp.model.appbean.ConcernFansBean;
import zhonghuass.ssml.mvp.model.appbean.DailyBean;
import zhonghuass.ssml.mvp.model.appbean.DailyChoicenessBean;
import zhonghuass.ssml.mvp.model.appbean.DanynimicBean;
import zhonghuass.ssml.mvp.model.appbean.DiscussBean;
import zhonghuass.ssml.mvp.model.appbean.EPLoginBean;
import zhonghuass.ssml.mvp.model.appbean.FocusBean;
import zhonghuass.ssml.mvp.model.appbean.GraphicBean;
import zhonghuass.ssml.mvp.model.appbean.HistoryBean;
import zhonghuass.ssml.mvp.model.appbean.IniviteBean;
import zhonghuass.ssml.mvp.model.appbean.LoginBean;
import zhonghuass.ssml.mvp.model.appbean.PWLoginBean;
import zhonghuass.ssml.mvp.model.appbean.PhotoBean;
import zhonghuass.ssml.mvp.model.appbean.RecomDetailBean;
import zhonghuass.ssml.mvp.model.appbean.RecommendBean;
import zhonghuass.ssml.mvp.model.appbean.SearchBean;
import zhonghuass.ssml.mvp.model.appbean.ShareMeBean;
import zhonghuass.ssml.mvp.model.appbean.TradeBean;
import zhonghuass.ssml.mvp.model.appbean.TradeItemBean;
import zhonghuass.ssml.mvp.model.appbean.UserInfoBean;

public interface ApiServer {
    //注册
    @FormUrlEncoded
    @POST("/Api/Register/register")
    Observable<BaseResponse<Void>> toRegist(@Field("mobile") String mPhone,
                                            @Field("password") String mPass,
                                            @Field("code") String mCode);

    //获取验证码
    @GET("/Api/Service/getyzm")
    Observable<BaseResponse<Void>> getCode(@Query("mobile") String mPhone, @Query("type") String type);

    //企业名录
    @GET("Api/Enterprise/homepage")
    Observable<BaseResponse<List<TradeBean>>> getTradeData(@Query("areaid") String area, @Query("servicetype") String type,
                                                           @Query("page") String page, @Query("pagesize") String pagesize);

    //手机号验证码登录
    @FormUrlEncoded
    @POST("/Api/Login/login")
    Observable<LoginBean> toLogin(@Field("username") String mPhone,
                                  @Field("code") String mCode,
                                  @Field("type") String s);

    //密码登录
    @FormUrlEncoded
    @POST("/Api/Login/login")
    Observable<PWLoginBean> topwLogin(@Field("username") String mPhone,
                                      @Field("password") String mPassworld,
                                      @Field("type") String s);

    //企业登录
    @FormUrlEncoded
    @POST("/Api/Login/login")
    Observable<EPLoginBean> toepLogin(@Field("username") String mPhone,
                                      @Field("password") String mPassworld,
                                      @Field("type") String s);

    //验证验证码正确
    @FormUrlEncoded
    @POST("/Api/Login/login")
    Observable<BaseResponse<Void>> toForgetPassworldVerification(@Field("username") String mPhone,
                                                                 @Field("code") String mCode,
                                                                 @Field("type") String s);
    @FormUrlEncoded
    @POST("/Api/Login/forgetpwd")
    Observable<BaseResponse<Void>> toConfirModi(@Field("mobile") String phone
            , @Field("code") String code
            , @Field("pwd") String newpw
            , @Field("pwds") String oldpw);

    //分享我
    @GET("Api/record/share")
    Observable<BaseResponse<List<ShareMeBean>>> getShareMeData(@Query("member_id") String area, @Query("member_type") String type,
                                                               @Query("page") String page);

    //关注我
    @GET("Api/record/concern")
    Observable<BaseResponse<List<ShareMeBean>>> getConcernData(@Query("member_id") String area, @Query("member_type") String type,
                                                               @Query("page") String page);

    //赞我
    @GET("Api/record/praise")
    Observable<BaseResponse<List<ShareMeBean>>> getPraiseData(@Query("member_id") String area, @Query("member_type") String type,
                                                              @Query("page") String page);

    //评论我   Api/comment/record
    @GET("Api/comment/record")
    Observable<BaseResponse<List<CommentBean>>> getCommentData(@Query("member_id") String area, @Query("member_type") String type,
                                                               @Query("page") String page);

    //搜索内容
    @GET("Api/search/search")
    Observable<BaseResponse<List<RecommendBean>>> getSearchResultData(@Query("member_id") String area, @Query("member_type") String type,
                                                                      @Query("page") String page);

    //首页推荐
    @GET("/Api/content/recommend")
    Observable<BaseResponse<List<RecommendBean>>> getRecommendDatas(@Query("member_id") String member_id,
                                                                    @Query("member_type") String member_type, @Query("page") String page);

    //获取我的关注
    @GET("/Api/Concern/concern")
    Observable<BaseResponse<List<ConcernFansBean>>> getMyConcernData(@Query("member_id") String member_id,
                                                                     @Query("member_type") String member_type,
                                                                     @Query("page") int page);

    //获取我的粉丝
    @GET("/Api/Concern/fans")
    Observable<BaseResponse<List<ConcernFansBean>>> getMyFansData(@Query("member_id") String member_id,
                                                                  @Query("member_type") String member_type,
                                                                  @Query("page") int page);

    //搜索内容  Api/search/search
    @GET("/Api/search/search")
    Observable<BaseResponse<List<SearchBean>>> getSearchData(@Query("search_content") String search_content,
                                                             @Query("member_id") String member_id,
                                                             @Query("member_type") String member_type,
                                                             @Query("page") String page);

    //历史搜索
    @GET("/Api/search/history")
    Observable<BaseResponse<List<HistoryBean>>> getHistoryData(@Query("member_id") String member_id,
                                                               @Query("member_type") String member_type);

    @POST("/Api/search/delete")
    Observable<BaseResponse<Void>> deleteSearchHistory(@Query("member_id") String member_id,
                                                       @Query("member_type") String member_type);


    //取消关注
    @FormUrlEncoded
    @POST("/Api/Concern/cancel")
    Observable<BaseResponse<Void>> toCancelConcern(@Field("member_id") String member_id,
                                                   @Field("member_type") String member_type,
                                                   @Field("target_id") String target_id,
                                                   @Field("target_type") String target_type);

    //关注
    @FormUrlEncoded
    @POST("/Api/Concern/add")
    Observable<BaseResponse<Void>> toConcern(@Field("member_id") String member_id,
                                             @Field("member_type") String member_type,
                                             @Field("target_id") String target_id,
                                             @Field("target_type") String target_type);

    //每日一语排行  /Api/content/themecount
    @GET("/Api/content/themecount")
    Observable<DailyBean> getDailyHeaderData();

    //获取我的粉丝
    @GET("/Api/content/theme")
    Observable<BaseResponse<List<DailyChoicenessBean>>> getDailyData(@Query("member_id") String member_id,
                                                                     @Query("member_type") String member_type,
                                                                     @Query("page") String page);

    @GET("/Api/content/trends")
    Observable<BaseResponse<List<DanynimicBean>>> getDanymicData(@Query("member_id") String member_id,
                                                                 @Query("member_type") String member_type,
                                                                 @Query("page") String page);

    @GET("/Api/content/concern")
    Observable<BaseResponse<List<FocusBean>>> getFocusData(@Query("member_id") String member_id,
                                                           @Query("member_type") String member_type,
                                                           @Query("page") String page);

    @GET("/Api/content/access")
    Observable<BaseResponse<List<PhotoBean>>> getPhotoData(@Query("target_id") String eid, @Query("target_type") String target_type, @Query("content_type") String content_type,
                                                           @Query("member_id") String member_id,
                                                           @Query("member_type") String member_type,
                                                           @Query("page") String page);

    //获取我的收藏
    @GET("/Api/content/mycollection")
    Observable<BaseResponse<List<CollectionBean>>> getMyCollection(@Query("member_id") String member_id,
                                                                   @Query("member_type") String member_type,
                                                                   @Query("page") int page);

    //取消收藏

    @FormUrlEncoded
    @POST("/Api/content/cancelcollection")
    Observable<BaseResponse<Void>> toCancelCollection(@Field("member_id") String member_id,
                                                      @Field("member_type") String member_type,
                                                      @Field("content_id") String content_id);

    //盛世名录-产品
    @GET("/api/Enterprise/product")
    Observable<BaseResponse<ComanyrfBean>> getcomanyrfData(@Query("eid") String ep_id,
                                                           @Query("page") int page,
                                                           @Query("pagesize") int pagesize);

    //图文详情
    @GET("/Api/content/detail")
    Observable<GraphicBean> getGraphicData(@Query("content_id") String member_id,
                                           @Query("member_id") String member_type,
                                           @Query("member_type") String content_id);

    //招聘
    @GET("/api/Enterprise/recruitment")
    Observable<BaseResponse<IniviteBean>> getInviteData(@Query("eid") String ep_id,
                                                        @Query("page") int page,
                                                        @Query("pagesize") int pagesize);

    //上传视频
    @Multipart
    @POST("/Api/content/publish")
    Observable<BaseResponse<Void>> upLoadData(@PartMap Map<String, RequestBody> map, @Part MultipartBody.Part mediaparts, @Part MultipartBody.Part mediaPart); //,视频

    //上传图片
    @Multipart
    @POST("/Api/content/publish")
    Observable<BaseResponse<Void>> upLoadImages(@PartMap Map<String, RequestBody> map, @Part MultipartBody.Part file, @Part MultipartBody.Part[] parts);

    //详情页推荐
    @GET("/Api/theme/recommend")
    Observable<BaseResponse<List<RecomDetailBean>>> getRecomDetail();

    //区域分类
    @GET("/Api/Enterprise/getAreaContent")
    Observable<ResponseBody> getAreaData();

    //行业选择
    @GET("/Api/Enterprise/getIndustryContent")
    Observable<BaseResponse<List<TradeItemBean>>> getTradeItem();

    //公司简介
    @GET("/Api/Enterprise/synopsis")
    Observable<BaseResponse<List<BriefBean>>> getBriefData(@Query("eid") String eid);

    //内容点赞
    @FormUrlEncoded
    @POST("/Api/content/praise")
    Observable<BaseResponse<Void>> addLike(@Field("member_id") String user_id, @Field("content_id") String content_id,
                                           @Field("member_type") String user_type);

    //收藏
    @FormUrlEncoded
    @POST("/Api/content/collection")
    Observable<BaseResponse<Void>> addColect(@Field("member_id") String user_id, @Field("content_id") String content_id, @Field("member_type") String user_type);

    //评论列表
    @GET("/Api/comment/list")
    Observable<BaseResponse<List<DiscussBean>>> getDiscussData(@Query("content_id") String content_id, @Query("member_id") String member_id, @Query("member_type") String member_type, @Query("page") String page);

    //评论
    @FormUrlEncoded
    @POST("/Api/comment/comment")
    Observable<BaseResponse<Void>> addPublish(@Field("member_id") String user_id, @Field("member_type") String member_type, @Field("content_id") String content_id, @Field("comment_detail") String mContext);

    @FormUrlEncoded
    @POST("/Api/content/praise")
    Observable<BaseResponse<Void>> addContentZan(@Field("member_id") String user_id, @Field("member_type") String user_type, @Field("content_id") String comment_id);

    //更新个人资料
    @Multipart
    @POST("/Api/Member/Editing")
    Observable<BaseResponse<List<UserInfoBean>>> updateMyInfo(@PartMap Map<String, RequestBody> map, @Part MultipartBody.Part file);


}

