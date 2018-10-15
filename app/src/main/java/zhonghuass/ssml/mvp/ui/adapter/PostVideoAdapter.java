package zhonghuass.ssml.mvp.ui.adapter;


import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;

import java.util.List;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.model.appbean.RecomDetailBean;

public class PostVideoAdapter extends BaseQuickAdapter<RecomDetailBean, BaseViewHolder> {

    public PostVideoAdapter(int postvideos_item, List<RecomDetailBean> list) {
        super(postvideos_item, list);
    }
    @Override
    protected void convert(BaseViewHolder helper,RecomDetailBean item) {
        helper.setText(R.id.tv_name,item.getTheme_title());
    }
}
