package zhonghuass.ssml.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.jess.arms.http.imageloader.glide.BlurTransformation;
import com.jess.arms.http.imageloader.glide.GlideOptions;

import java.io.File;

/**
 * Glide 图片加载框架
 * <p>
 * Glide加载图片的封装，圆形、圆角，模糊等处理操作
 * <p>
 * Glide默认使用httpurlconnection协议，可以配置为OkHttp
 * <p>
 * 磁盘缓存的策略：
 * all:缓存源资源和转换后的资源
 * none:不作任何磁盘缓存
 * source:缓存源资源
 * result：缓存转换后的资源
 */
public class GlideUtils {

    private static GlideUtils mInstance;

    private GlideUtils() {
    }

    public static GlideUtils getInstance() {
        if (mInstance == null) {
            synchronized (GlideUtils.class) {
                if (mInstance == null) {
                    mInstance = new GlideUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 常量
     */
    static class Contants {
        public static final int BLUR_VALUE = 20; //模糊
        public static final int CORNER_RADIUS = 20; //圆角
        public static final float THUMB_SIZE = 0.5f; //0-1之间  10%原图的大小
    }

    ///////////////////////////////////////  具 体 方 法  ////////////////////////////////////////////

    /**
     * 默认glide，不做任何处理，glide 从字符串中加载图片（网络地址或者本地地址）
     */
    public static void intoDefault(Context context, String url, ImageView view) {
        Glide.with(context).load(url).apply(MGlideOptions.getOptions()).into(view);
    }

    /**
     * 默认glide，不做任何处理，加载资源图片
     */
    public static void intoDefault(Context context, int id, ImageView view) {
        Glide.with(context).load(id).apply(MGlideOptions.getOptions()).into(view);
    }


    /**
     * glide 从URI中加载图片
     */
    public static void into(Context context, Uri uri, ImageView view) {
        Glide.with(context).load(uri)
                .apply(MGlideOptions.getOptions()).into(view);
    }

    /**
     * glide 从文件中加载图片
     */
    public static void into(Context context, File file, ImageView view) {
        Glide.with(context).load(file)
                .apply(MGlideOptions.getOptions()).into(view);
    }

    /**
     * 恢复请求，一般在停止滚动的时候
     *
     * @param context
     */
    public void resumeRequests(Context context) {
        Glide.with(context).resumeRequests();
    }

    /**
     * 暂停请求 正在滚动的时候
     *
     * @param context
     */
    public void pauseRequests(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 清理磁盘缓存
     *
     * @param mContext
     */
    public static void GuideClearDiskCache(Context mContext) {
        //理磁盘缓存 需要在子线程中执行
        Glide.get(mContext).clearDiskCache();
    }

    /**
     * 清理内存缓存
     *
     * @param mContext
     */
    public static void GuideClearMemory(Context mContext) {
        //清理内存缓存  可以在UI主线程中进行
        Glide.get(mContext).clearMemory();
    }


}
