package zhonghuass.ssml.mvp.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;

import java.util.List;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.model.appbean.RecommendBean;

public class RecommendAdapter extends BaseQuickAdapter<RecommendBean, BaseViewHolder> {
    public RecommendAdapter(int layoutResId, List<RecommendBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendBean item) {

        helper.setText(R.id.company_name, item.companyName);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.override(Target.SIZE_ORIGINAL);
        requestOptions.dontAnimate();

        Glide.with(mContext)
                .load(item.imgPath)
                .apply(requestOptions)
                .into((ImageView) helper.getView(R.id.recommend_img));

    }
}
