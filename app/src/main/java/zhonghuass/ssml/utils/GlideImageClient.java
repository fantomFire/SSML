package zhonghuass.ssml.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import qdx.bezierviewpager_compile.util.ImageLoadClient;

public class GlideImageClient extends ImageLoadClient {
    @Override
    public void loadImage(ImageView imageView, Object o, Context context) {
        Glide.with(context).load(o).into(imageView);
    }
}
