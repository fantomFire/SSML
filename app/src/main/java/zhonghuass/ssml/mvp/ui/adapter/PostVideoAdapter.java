package zhonghuass.ssml.mvp.ui.adapter;


import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;

import java.util.List;

import zhonghuass.ssml.mvp.model.appbean.IniviteBean;

public class PostVideoAdapter extends BaseQuickAdapter<IniviteBean.ListBean, BaseViewHolder> {

    public PostVideoAdapter(int postvideos_item, List<IniviteBean.ListBean> list) {
        super(postvideos_item, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, IniviteBean.ListBean item) {

    }
}
