package zhonghuass.ssml.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;

import java.util.List;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.model.appbean.HistoryBean;

public class SearchHistoryAdapter extends BaseQuickAdapter<HistoryBean, BaseViewHolder> {
    public SearchHistoryAdapter(int layoutResId, @Nullable List<HistoryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryBean item) {
        helper.setText(R.id.tv_history,item.getSearch_content());
        //item点击事件
        helper.convertView.setOnClickListener((v) -> {
                    System.out.println("点击了" + item);
                }


        );
    }
}
