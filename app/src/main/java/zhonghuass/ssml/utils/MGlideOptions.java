package zhonghuass.ssml.utils;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class MGlideOptions {

    public static RequestOptions requestOptions;

    public static RequestOptions getOptions() {
        requestOptions = new RequestOptions();
        requestOptions.override(Target.SIZE_ORIGINAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate();
        return requestOptions;
    }

    public static RequestOptions getOptions(int id) {
        requestOptions = new RequestOptions();
        requestOptions.override(Target.SIZE_ORIGINAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .error(id);
        return requestOptions;
    }

}
