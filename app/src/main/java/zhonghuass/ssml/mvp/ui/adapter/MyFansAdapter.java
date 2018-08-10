package zhonghuass.ssml.mvp.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;

import java.util.List;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.model.appbean.ConcernFansBean;
import zhonghuass.ssml.utils.GlideUtils;

public class MyFansAdapter extends BaseQuickAdapter<ConcernFansBean, BaseViewHolder> {
    public MyFansAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ConcernFansBean item) {
        helper.addOnClickListener(R.id.tv_concern);
        TextView textView = helper.getView(R.id.tv_concern);
        helper.setText(R.id.tv_concern_name, item.member_name);
        GlideUtils.intoDefault(mContext, item.member_image, (ImageView) helper.getView(R.id.civ_photo));
        if (item.mutual_concern.equals("0")) {
            textView.setText("+关注");
            textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_concern_0));
        }
        if (item.mutual_concern.equals("1")) {
            textView.setText("互相关注");
            textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_concern_1));
        }
    }

}
