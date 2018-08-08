package zhonghuass.ssml.mvp.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;

import java.util.List;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.model.appbean.ShareMeBean;

public class PraiseAdapter extends BaseQuickAdapter<ShareMeBean,BaseViewHolder> {

    public PraiseAdapter(int layoutResId, List<ShareMeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShareMeBean item) {
        helper.setText(R.id.tv_share_item_name,item.getMember_name())
                .setText(R.id.tv_share_item_date,item.getAdd_time())
                .setText(R.id.tv_about_me, "赞了我");
        Glide.with(mContext)
                .load(item.getMember_image())
                .into((ImageView)helper.getView(R.id.civ_share_item_icon));
        //item点击事件
        helper.convertView.setOnClickListener((v) ->{
                    System.out.println("点击了"+item);
                }


        );
    }
}
