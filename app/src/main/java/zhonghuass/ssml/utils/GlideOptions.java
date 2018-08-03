package zhonghuass.ssml.utils;

import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public  class GlideOptions {

    private RequestOptions requestOptions;

    public RequestOptions getOptions(int id ){
        requestOptions = new RequestOptions();
        requestOptions.override(Target.SIZE_ORIGINAL);
        requestOptions.dontAnimate();
        requestOptions.error(id);
        return requestOptions;
    }

}
