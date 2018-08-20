package zhonghuass.ssml.mvp.ui.adapter;


import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;


import java.util.List;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.model.appbean.IniviteBean;
import zhonghuass.ssml.utils.GlideUtils;

public class IniviteAdapter extends BaseQuickAdapter<IniviteBean.ListBean, BaseViewHolder> {


    public IniviteAdapter(int layoutResId, @Nullable List<IniviteBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IniviteBean.ListBean item) {
        GlideUtils.intoDefault(mContext, item.logo, (ImageView) helper.getView(R.id.iv_compony));
        helper.setText(R.id.tv_inivite_hir,item.hiring)
                .setText(R.id.tv_inivite_salary,item.salary)
                .setText(R.id.tv_inivite_shortname,item.shortname)
                .setText(R.id.tv_inivite_1,item.work_address)
                .setText(R.id.tv_inivite_2,item.xueli)
                .setText(R.id.tv_inivite_3,item.work_experience)
                .setText(R.id.tv_compony_4,item.linkman)
                .setText(R.id.tv_compony_5,item.add_time);
    }
}
