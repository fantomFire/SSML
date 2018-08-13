package zhonghuass.ssml.mvp.ui.adapter;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;
import com.github.library.layoutView.CircleImageView;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.model.appbean.RecommendBean;


public class RecommendAdapter extends BaseQuickAdapter<RecommendBean, BaseViewHolder> {

    private List<RecommendBean> mDataList;

    public RecommendAdapter(int layoutResId, List<RecommendBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper,final RecommendBean item) {
       final String cover_width = item.getCover_width();
      final   String cover_height = item.getCover_height();
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

        //requestOptions.set();
        int screenWidth = ArmsUtils.getScreenWidth(mContext);
        int imgWidth = (screenWidth -30)/2;
        int resize = Integer.parseInt(cover_width)/imgWidth;
        int imghight = Integer.parseInt(cover_height)/resize;

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) helper.getView(R.id.recommend_img).getLayoutParams();
        layoutParams.width = imgWidth;
        layoutParams.height = imghight;
        helper.getView(R.id.recommend_img).setLayoutParams(layoutParams);

       /* RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(imgWidth,imghight);
        helper.getView(R.id.recommend_img).setLayoutParams(params);*/


        Glide.with(mContext)
                .load(item.getContent_cover())
             // .apply(requestOptions)
                .into((ImageView) helper.getView(R.id.recommend_img));
        Glide.with(mContext)
                .load(item.getMember_image())
                //.apply(requestOptions)
                .into((CircleImageView) helper.getView(R.id.company_icon));

    }


}
