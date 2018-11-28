package zhonghuass.ssml.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;

import java.util.List;

import zhonghuass.ssml.R;
import zhonghuass.ssml.mvp.ui.activity.SelectMBActivity;
import zhonghuass.ssml.utils.GlideUtils;

public class TemplateAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public TemplateAdapter(int imgselect, List<String> mList) {
        super(imgselect, mList);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView mImage = helper.getView(R.id.mImage);
        GlideUtils.getInstance().intoDefault(mContext,item,mImage);
    }
}
