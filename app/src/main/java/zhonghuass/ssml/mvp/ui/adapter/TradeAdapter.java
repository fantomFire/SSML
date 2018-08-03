package zhonghuass.ssml.mvp.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;

import java.util.List;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.model.appbean.TradeBean;

public class TradeAdapter extends BaseQuickAdapter<TradeBean,BaseViewHolder> {
    public TradeAdapter(int trade_item,List<TradeBean> data) {
        super(trade_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TradeBean item) {
        helper.setText(R.id.trade_name,item.getShortname())
                .setText(R.id.tv_type,item.getServicetype())
                .setText(R.id.tv_phone,item.getPhone())
                .setText(R.id.tv_address,item.getAddr());
        System.out.println(item.getShortname());
        Glide.with(mContext)
                .load(item.getLogo())
                .into((ImageView)helper.getView(R.id.trade_log));


    }
}
