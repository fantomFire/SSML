package zhonghuass.ssml.mvp.ui.adapter;

import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseSectionQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;

import java.util.List;

import zhonghuass.ssml.mvp.model.appbean.SysetemBean;

public class SysetemMessageAdapter extends BaseQuickAdapter<SysetemBean,BaseViewHolder> {

    public SysetemMessageAdapter(int layoutResId, List<SysetemBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SysetemBean item) {

    }
}
