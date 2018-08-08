package zhonghuass.ssml.http;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import zhonghuass.ssml.mvp.model.appbean.CommentBean;
import zhonghuass.ssml.mvp.model.appbean.ShareMeBean;
import zhonghuass.ssml.mvp.model.appbean.TradeBean;

public interface ApiServer {
    //注册
    @GET("/Api/Register/register")
    Observable<BaseResponse<Void>> toRegist(@Query("mobile") String mPhone,
                                            @Query("password") String mPass,
                                            @Query("code") String mCode);

    //获取验证码
    @GET("/Api/Service/getyzm")
    Observable<BaseResponse<Void>> getCode(@Query("mobile") String mPhone, @Query("type") String type);

    //企业名录
    @GET("Api/Enterprise/homepage")
    Observable<BaseResponse<List<TradeBean>>> getTradeData(@Query("areaid") String area, @Query("servicetype") String type,
                                                           @Query("page") String page, @Query("pagesize") String pagesize);

    //手机号验证码登录
    @GET("/Api/Login/login")
    Observable<BaseResponse<Void>> toLogin(@Query("username") String mPhone,
                                           @Query("code") String mCode,
                                           @Query("type") String s);

    //密码登录,企业登录
    @GET("/Api/Login/login")
    Observable<BaseResponse<Void>> topwLogin(@Query("username") String mPhone,
                                             @Query("password") String mPassworld,
                                             @Query("type") String s);

    //验证验证码正确
    @GET("/Api/Login/forgetpwd")
    Observable<BaseResponse<Void>> toForgetPassworldVerification(@Query("mobile") String mPhone,
                                                                 @Query("code") String mCode);

    @GET("/Api/Login/forgetpwd")
    Observable<BaseResponse<Void>> toConfirModi(@Query("mobile") String phone
            , @Query("code ") String code
            , @Query("pwd") String newpw
            , @Query("pwds") String oldpw);
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
}
