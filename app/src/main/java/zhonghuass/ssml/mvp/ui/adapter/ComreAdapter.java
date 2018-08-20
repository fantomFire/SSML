package zhonghuass.ssml.mvp.ui.adapter;


import android.widget.ImageView;

import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;

import java.util.List;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.model.appbean.ComanyrfBean;
import zhonghuass.ssml.utils.GlideUtils;

public class ComreAdapter extends BaseQuickAdapter<ComanyrfBean.ListBean, BaseViewHolder> {

    private final List<ComanyrfBean.ListBean> list;

    public ComreAdapter(int layoutResId, List<ComanyrfBean.ListBean> list) {
        super(layoutResId,list);
        this.list =list;
    }

    @Override
    protected void convert(BaseViewHolder helper, ComanyrfBean.ListBean item) {

        GlideUtils.intoDefault(mContext, item.logo, (ImageView) helper.getView(R.id.iv_compony));
        helper.setText(R.id.tv_compony_1,item.title)
                .setText(R.id.tv_compony_2,item.description)
                .setText(R.id.tv_compony_3,item.shortname)
                .setText(R.id.tv_compony_4,item.linkman)
                .setText(R.id.tv_compony_5,item.add_time);
    }
}
