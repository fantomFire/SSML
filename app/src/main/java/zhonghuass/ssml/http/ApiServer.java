package zhonghuass.ssml.http;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import zhonghuass.ssml.mvp.model.appbean.CommentBean;
import zhonghuass.ssml.mvp.model.appbean.ConcernFansBean;
import zhonghuass.ssml.mvp.model.appbean.HistoryBean;
import zhonghuass.ssml.mvp.model.appbean.LoginBean;
import zhonghuass.ssml.mvp.model.appbean.MessageListBean;
import zhonghuass.ssml.mvp.model.appbean.RecommendBean;
import zhonghuass.ssml.mvp.model.appbean.SearchBean;
import zhonghuass.ssml.mvp.model.appbean.ShareMeBean;
import zhonghuass.ssml.mvp.model.appbean.TradeBean;

public interface ApiServer {
    //注册
    @FormUrlEncoded
    @POST("/Api/Register/register")
    Observable<BaseResponse<Void>> toRegist(@Field("mobile") String mPhone,
                                            @Field("password") String mPass,
                                            @Field("code") String mCode);

    //获取验证码
    @GET("/Api/Service/getyzm")
    Observable<BaseResponse<Void>> getCode(@Query("mobile") String mPhone,
                                           @Query("type") String type);

    //企业名录
    @GET("Api/Enterprise/homepage")
    Observable<BaseResponse<List<TradeBean>>> getTradeData(@Query("areaid") String area,
                                                           @Query("servicetype") String type,
                                                           @Query("page") String page,
                                                           @Query("pagesize") String pagesize);

    //手机号验证码登录
    @FormUrlEncoded
    @POST("/Api/Login/login")
    Observable<BaseResponse<LoginBean>> toLogin(@Field("username") String mPhone,
                                                @Field("code") String mCode,
                                                @Field("type") String s);

    //密码登录,企业登录
    @FormUrlEncoded
    @POST("/Api/Login/login")
    Observable<BaseResponse<LoginBean>> topwLogin(@Field("username") String mPhone,
                                                  @Field("password") String mPassworld,
                                                  @Field("type") String s);

    //验证验证码正确
    @GET("/Api/Login/forgetpwd")
    Observable<BaseResponse<Void>> toForgetPassworldVerification(@Query("mobile") String mPhone,
                                                                 @Query("code") String mCode);

    @GET("/Api/Login/forgetpwd")
    Observable<BaseResponse<Void>> toConfirModi(
            @Query("mobile") String phone
            , @Query("code ") String code
            , @Query("pwd") String newpw
            , @Query("pwds") String oldpw);

    //分享我
    @GET("/Api/record/share")
    Observable<BaseResponse<List<ShareMeBean>>> getShareMeData(@Query("member_id") String area,
                                                               @Query("member_type") String type,
                                                               @Query("page") String page);

    //关注我
    @GET("/Api/record/concern")
    Observable<BaseResponse<List<ShareMeBean>>> getConcernData(@Query("member_id") String area,
                                                               @Query("member_type") String type,
                                                               @Query("page") String page);

    //赞我
    @GET("/Api/record/praise")
    Observable<BaseResponse<List<ShareMeBean>>> getPraiseData(@Query("member_id") String area,
                                                              @Query("member_type") String type,
                                                              @Query("page") String page);

    //评论我
    @GET("/Api/comment/record")
    Observable<BaseResponse<List<CommentBean>>> getCommentData(@Query("member_id") String area,
                                                               @Query("member_type") String type,
                                                               @Query("page") String page);

    //私信列表
    @GET("")
    Observable<BaseResponse<List<MessageListBean>>> getMessageListData(@Query("member_id") String area,
                                                                       @Query("member_type") String type,
                                                                       @Query("page") String page);

    //首页推荐
    @GET("/Api/content/recommend")
    Observable<BaseResponse<List<RecommendBean>>> getRecommendDatas(@Query("member_id") String member_id,
                                                                    @Query("member_type") String member_type,
                                                                    @Query("page") String page);

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
                                                             @Query("page") int page);

    //历史搜索
    @GET("/Api/search/history")
    Observable<BaseResponse<List<HistoryBean>>> getHistoryData(@Query("member_id") String member_id,
                                                               @Query("member_type") String member_type);

    @POST("/Api/search/delete")
    Observable<BaseResponse<Void>> deleteSearchHistory(@Query("member_id") String member_id,
                                                       @Query("member_type") String member_type);

}
