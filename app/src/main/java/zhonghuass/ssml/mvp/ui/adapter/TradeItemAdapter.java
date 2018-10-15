package zhonghuass.ssml.mvp.ui.adapter;

import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;

import java.util.List;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.model.appbean.TradeItemBean;

public class TradeItemAdapter extends BaseQuickAdapter<TradeItemBean,BaseViewHolder> {
    public TradeItemAdapter(int trade_items, List<TradeItemBean> tradeData) {
        super(trade_items,tradeData);
    }

    @Override
    protected void convert(BaseViewHolder helper, TradeItemBean item) {
        helper.setText(R.id.trade_name,item.getName());
    }
}
