package zhonghuass.ssml.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;

import java.util.List;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.model.appbean.DiscussBean;

public class DiscussAdapter extends BaseQuickAdapter<DiscussBean,BaseViewHolder> {
    public DiscussAdapter(int discuss_item, List<DiscussBean> mList) {
        super(discuss_item,mList);
    }

    @Override
    protected void convert(BaseViewHolder helper, DiscussBean item) {
        ImageView imgUser = helper.getView(R.id.img_user);
        Glide.with(mContext)
                .load(item.getMember_image())
                .into(imgUser);
        helper.setText(R.id.user_name,item.getMember_name())
                .setText(R.id.dis_context,item.getComment_detail());

        ImageView img_zan = helper.getView(R.id.img_zan);
        boolean praise_tag = item.isPraise_tag();
        if(praise_tag){
            img_zan.setBackgroundResource(R.mipmap.ml_icon_16);
        }else {
            img_zan.setBackgroundResource(R.mipmap.ml_icon_20);
        }
        helper.addOnClickListener(R.id.ll_tolike);

    }
}
