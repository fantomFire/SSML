package zhonghuass.ssml.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;

import java.util.List;

import zhonghuass.ssml.R;

public class LaurelAdapter extends BaseQuickAdapter<String ,BaseViewHolder> {
    public LaurelAdapter(int ids, List<String> data) {
        super(ids,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView mImage = helper.getView(R.id.laurel_img);
        Glide.with(mContext)
                .load(item)
                .into(mImage);
    }
}
