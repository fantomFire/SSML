package zhonghuass.ssml.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.library.baseAdapter.BaseQuickAdapter;
import com.github.library.baseAdapter.BaseViewHolder;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import zhonghuass.ssml.R;

public class ImagesAdapter extends BaseQuickAdapter<LocalMedia, BaseViewHolder> {

    private ArrayList<LocalMedia> localMedia;

    public ImagesAdapter(int layoutResId, ArrayList<LocalMedia> imagesList) {
        super(layoutResId, imagesList);
        localMedia = imagesList;
    }


    @Override
    protected void convert(BaseViewHolder holder, LocalMedia item) {
        ImageView mImage = (ImageView) holder.getView(R.id.img_back);
        ImageView image_delete = (ImageView) holder.getView(R.id.image_delete);
        System.out.println("NNNN" + item.getPath());
        Glide.with(mContext)
                .load(item.getPath())
                .into(mImage);

        final int adapterPosition = holder.getAdapterPosition();

        if (adapterPosition == localMedia.size() - 1) {
            image_delete.setVisibility(View.GONE);
        } else {
            image_delete.setVisibility(View.VISIBLE);
        }

        holder.getView(R.id.image_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localMedia.remove(adapterPosition);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (localMedia.size() > 9) {

            return 9;
        } else {
            return localMedia.size();
        }
    }
}
