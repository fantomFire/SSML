package zhonghuass.ssml.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.github.library.layoutView.CircleImageView;
import com.jess.arms.utils.ArmsUtils;
import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.model.appbean.RecommendBean;
import zhonghuass.ssml.mvp.ui.ScaleImageView;

import java.util.List;

/**
 * Created by long on 2016/10/11.
 * 福利图适配器
 */
public class StaggeredGridAdapter extends BaseQuickAdapter<RecommendBean> {

    // 图片的宽度
    private int mPhotoWidth;


    public StaggeredGridAdapter(Context context) {
        super(context);
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        int marginPixels = 1;
        mPhotoWidth = widthPixels / 2 - marginPixels;
    }

    public StaggeredGridAdapter(Context context, List<RecommendBean> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.recommend_item;
    }


    @Override
    protected void convert(final BaseViewHolder holder, final RecommendBean item) {
        holder.setText(R.id.company_name, item.getMember_name())
                .setText(R.id.company_name, item.getContent_title());

        final String cover_width = item.getCover_width();
        final String cover_height = item.getCover_height();
        int screenWidth = ArmsUtils.getScreenWidth(mContext);
        int imgWidth = (screenWidth - 30) / 2;
        int resize = Integer.parseInt(cover_width) / imgWidth;
        int imghight = Integer.parseInt(cover_height) / resize;

        ScaleImageView iv = (ScaleImageView) holder.getView(R.id.recommend_img);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.getView(R.id.recommend_img).getLayoutParams();
        layoutParams.width = imgWidth;
        layoutParams.height = imghight;
        iv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shape_iv_bg));
        iv.setInitSize(imgWidth, imghight);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.shape_iv_bg);
        Glide.with(mContext)
                .load(item.getContent_cover())
//                .apply(requestOptions)
                .into(iv);
        Glide.with(mContext)
                .load(item.getMember_image())
                //.apply(requestOptions)
                .into((CircleImageView) holder.getView(R.id.company_icon));
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}