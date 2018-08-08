package zhonghuass.ssml.mvp.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;
import com.github.library.layoutView.CircleImageView;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.model.appbean.RecommendBean;


public class RecommendAdapter extends BaseQuickAdapter<RecommendBean, BaseViewHolder> {
    public RecommendAdapter(int layoutResId, List<RecommendBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendBean item) {

        helper.setText(R.id.company_name, item.getMember_name())
                .setText(R.id.company_name, item.getContent_title());

        TextView flag = helper.getView(R.id.tv_flag);
        String theme_title = item.getTheme_title();
        if (theme_title != null && !theme_title.equals("")) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(theme_title);
            spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#ffd800")), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            flag.setVisibility(View.VISIBLE);
            flag.setText(spannableStringBuilder);
        } else {
            flag.setVisibility(View.GONE);
        }

        RequestOptions requestOptions = new RequestOptions();
       // requestOptions.override(Target.SIZE_ORIGINAL);

        requestOptions.dontAnimate();
        int screenWidth = ArmsUtils.getScreenWidth(mContext);
        int imgWidth = (screenWidth -30)/2;
        int resize = Integer.parseInt(item.getCover_width())/imgWidth;
        int imghight = Integer.parseInt(item.getCover_height())/resize;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(imgWidth,imghight);
        helper.getView(R.id.recommend_img).setLayoutParams(params);

        requestOptions.override(ArmsUtils.pix2dip(mContext,imgWidth),ArmsUtils.pix2dip(mContext,imghight));

        System.out.println("imgWidth"+imgWidth);
        System.out.println("imghight"+imghight);
        Glide.with(mContext)
                .load(item.getContent_cover())
              .apply(requestOptions)
                .into((ImageView) helper.getView(R.id.recommend_img));
        Glide.with(mContext)
                .load(item.getMember_image())
                //.apply(requestOptions)
                .into((CircleImageView) helper.getView(R.id.company_icon));

    }
}
