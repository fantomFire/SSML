package zhonghuass.ssml.mvp.ui.adapter;

import android.widget.ImageView;

import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;

import java.util.List;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.model.appbean.ConcernFansBean;
import zhonghuass.ssml.utils.GlideUtils;

public class MyConcernAdapter extends BaseQuickAdapter<ConcernFansBean, BaseViewHolder> {
    public MyConcernAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ConcernFansBean item) {
        helper.setText(R.id.tv_concern_name, item.member_name);
        GlideUtils.intoDefault(mContext, item.member_image, (ImageView) helper.getView(R.id.civ_photo));
    }

}
