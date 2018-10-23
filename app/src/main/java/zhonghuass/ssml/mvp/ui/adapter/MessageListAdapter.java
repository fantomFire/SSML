package zhonghuass.ssml.mvp.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.model.appbean.MessageListBean;
import zhonghuass.ssml.mvp.ui.activity.ChattingActivity;

public class MessageListAdapter extends BaseQuickAdapter<MessageListBean.DataBean, BaseViewHolder> {

    public MessageListAdapter(int layoutResId, List<MessageListBean.DataBean> data) {
        super(layoutResId, data);
        //System.out.println(data.get(0).getMember()+"99999");
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageListBean.DataBean item) {
       /* helper.setText(R.id.tv_message_list_name,item.getMember_name())
                .setText(R.id.tv_message_list_date,item.getMessages().get(0).getAdd_time())
                .setText(R.id.tv_message_list_num,item.getUnread())
                .setText(R.id.tv_message_list_details,item.getMessages().get(0).getMessage());
        Glide.with(mContext)
                .load(item.getMember_image())
                .into((ImageView) helper.getView(R.id.civ_message_list_icon));
        //item点击事件
        helper.convertView.setOnClickListener((v) ->{
//            ArmsUtils.startActivity(ChattingActivity.class);
                }
*/

     //   );
    }
    }

