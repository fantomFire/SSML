package zhonghuass.ssml.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.github.library.layoutView.CircleImageView;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.model.appbean.DailyChoicenessBean;

public class DailyAdapter extends BaseQuickAdapter<DailyChoicenessBean, RecyclerView.ViewHolder> {

    public DailyAdapter(Context context, List<DailyChoicenessBean> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.recommend_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, DailyChoicenessBean item) {
        holder.setText(R.id.company_name, item.getMember_name())
                .setText(R.id.company_name, item.getContent_title());

        final String cover_width = item.getCover_width();
        final String cover_height = item.getCover_height();
        int screenWidth = ArmsUtils.getScreenWidth(mContext);
        int imgWidth = (screenWidth) / 2;
        int resize = Integer.parseInt(cover_width) / imgWidth;
        int imghight = Integer.parseInt(cover_height) / resize;

        ImageView iv = (ImageView) holder.getView(R.id.recommend_img);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.getView(R.id.recommend_img).getLayoutParams();
        layoutParams.width = imgWidth;
        layoutParams.height = imghight;
        iv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shape_iv_bg));
        iv.setLayoutParams(layoutParams);


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
