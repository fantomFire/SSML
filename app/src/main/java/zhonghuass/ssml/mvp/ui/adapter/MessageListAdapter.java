package zhonghuass.ssml.mvp.ui.adapter;

import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import zhonghuass.ssml.mvp.model.appbean.MessageListBean;
import zhonghuass.ssml.mvp.ui.activity.ChattingActivity;

public class MessageListAdapter extends BaseQuickAdapter<MessageListBean, BaseViewHolder> {

    public MessageListAdapter(int layoutResId, List<MessageListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageListBean item) {
        //item点击事件
        helper.convertView.setOnClickListener((v) ->{
//            ArmsUtils.startActivity(ChattingActivity.class);
                }


        );
    }
}
