package zhonghuass.ssml.mvp.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;

import java.util.ArrayList;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.model.appbean.RecommendBean;

public class RecommendAdapter extends BaseQuickAdapter<RecommendBean, BaseViewHolder> {
    public RecommendAdapter(int layoutResId, ArrayList<RecommendBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendBean item) {
        System.out.println("item.companyName"+item.companyName);
        helper.setText(R.id.company_name,item.companyName);

        Glide.with(mContext)
                .load(item.imgPath)
                .into((ImageView)helper.getView(R.id.recommend_img));

    }
}
