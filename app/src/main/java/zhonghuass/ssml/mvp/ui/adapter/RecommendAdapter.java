package zhonghuass.ssml.mvp.ui.adapter;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

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
    protected void convert(BaseViewHolder helper,final RecommendBean item) {


       ImageView logImage = (ImageView) helper.getView(R.id.recommend_img);
        int screenWidth = ArmsUtils.getScreenWidth(mContext);
       int imgwidth =  (screenWidth-10)/2;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)logImage.getLayoutParams();
        float ratio = Integer.parseInt(item.getCover_height())*1.0f /Integer.parseInt(item.getCover_width()) ;
        layoutParams.width = imgwidth;
        layoutParams.height = (int) (layoutParams.width*ratio);
        logImage.setLayoutParams(layoutParams);
        System.out.println("path"+item.getContent_cover());
        System.out.println("ratio"+ratio);
//https://blog.csdn.net/qq_33808060/article/details/59116624

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.dontAnimate();
        Glide.with(mContext)
                .load(item.getContent_cover())
                 .apply(requestOptions)
                .into(logImage);
        Glide.with(mContext)
                .load(item.getMember_image())
                //.apply(requestOptions)
                .into((CircleImageView) helper.getView(R.id.company_icon));

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



    }

}
