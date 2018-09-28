package zhonghuass.ssml.mvp.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 轮播图适配器
 */
public class StorePagerAdapter extends PagerAdapter {
    private Context context;
    List<String> bananer;
    private TextView tvBannertext;
    private boolean isclick;

    public StorePagerAdapter(Context context, List<String> banners) {
        this.context = context;
        this.bananer = banners;
    }

    @Override
    public int getCount() {


        return bananer == null ? 0 : bananer.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        Glide.with(context)
                .load(bananer.get(position))
                .into(imageView);


        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
