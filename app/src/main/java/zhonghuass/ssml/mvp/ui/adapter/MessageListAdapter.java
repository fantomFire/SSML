package zhonghuass.ssml.mvp.ui.adapter;

import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;

import java.util.List;

import zhonghuass.ssml.mvp.model.appbean.MessageListBean;

public class MessageListAdapter extends BaseQuickAdapter<MessageListBean, BaseViewHolder> {

    public MessageListAdapter(int layoutResId, List<MessageListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageListBean item) {

    }
}
