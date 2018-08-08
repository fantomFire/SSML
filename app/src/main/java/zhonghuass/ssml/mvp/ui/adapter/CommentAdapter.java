package zhonghuass.ssml.mvp.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;

import java.util.List;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.model.appbean.CommentBean;

public class CommentAdapter extends BaseQuickAdapter<CommentBean,BaseViewHolder> {

    public CommentAdapter(int layoutResId, List<CommentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentBean item) {
        helper.setText(R.id.tv_comment_name,item.getMember_name())
                .setText(R.id.tv_comment_date,item.getAdd_time())
                .setText(R.id.tv_comment_details, item.getComment_detail());
        Glide.with(mContext)
                .load(item.getMember_image())
                .into((ImageView)helper.getView(R.id.civ_comment_icon));
        //item点击事件
        helper.convertView.setOnClickListener((v) ->{
                    System.out.println("点击了"+item);
                }


        );

    }
}
