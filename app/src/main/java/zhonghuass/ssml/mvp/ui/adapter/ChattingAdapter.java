package zhonghuass.ssml.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;

import java.util.List;

import zhonghuass.ssml.mvp.model.appbean.ChatBean;

    public class ChattingAdapter extends BaseQuickAdapter<ChatBean, RecyclerView.ViewHolder> {


        public ChattingAdapter(Context context, List<ChatBean> data) {
            super(context, data);
        }

        @Override
        protected int attachLayoutRes() {
            return 0;
        }


        @Override
        protected void convert(BaseViewHolder holder, ChatBean item) {

        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return super.onCreateViewHolder(parent, viewType);

        }

        //    public ChattingAdapter(Context context, List<MultiItemEntity> data) {
//        super(context, data);
//    }
//
//    @Override
//    protected void attachItemType() {
////        addItemType(MultiItem.SEND, R.layout.chat_send_msg);
////        addItemType(MultiItem.FROM, R.layout.chat_from_msg);
//
//    }
//
//    @Override
//    protected void convert(BaseViewHolder holder, MultiItemEntity item) {
////        switch (item.itemType) {
////            case MultipleItem.HEADER:
////
////                break;
////            case MultipleItem.CONTENT:
////                helper.setText(R.id.tv_name, "小小淑" + item.name);
////                break;
////
////        }
//    }
}
