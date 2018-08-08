package zhonghuass.ssml.mvp.ui.adapter;

import android.util.Log;

import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;

import java.util.List;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.model.appbean.ConcernFansBean;

public class ConcernFansAdapter extends BaseQuickAdapter<ConcernFansBean, BaseViewHolder> {
    public ConcernFansAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ConcernFansBean item) {
        helper.setText(R.id.tv_concern_name, item.member_name);
    }

}
