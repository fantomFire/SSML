package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerImageEditorComponent;
import zhonghuass.ssml.di.module.ImageEditorModule;
import zhonghuass.ssml.mvp.contract.ImageEditorContract;
import zhonghuass.ssml.mvp.presenter.ImageEditorPresenter;
import zhonghuass.ssml.utils.ZoomImageView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ImageEditorActivity extends BaseActivity<ImageEditorPresenter> implements ImageEditorContract.View {

    @BindView(R.id.edit_img)
    ZoomImageView editImg;
    @BindView(R.id.out_bg)
    RelativeLayout outBg;
    @BindView(R.id.moban_item)
    LinearLayout mobanItem;
    @BindView(R.id.back_item)
    LinearLayout backItem;
    @BindView(R.id.name_item)
    LinearLayout nameItem;
    @BindView(R.id.mark_item)
    LinearLayout markItem;
    private List<LocalMedia> selectList;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerImageEditorComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .imageEditorModule(new ImageEditorModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_image_editor; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = this.getIntent();
        selectList = intent.getParcelableArrayListExtra("selectList");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }



    @OnClick({R.id.edit_img, R.id.moban_item, R.id.back_item, R.id.name_item, R.id.mark_item})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edit_img:
                break;
            case R.id.moban_item:
                break;
            case R.id.back_item:
                break;
            case R.id.name_item:
                break;
            case R.id.mark_item:
                break;
        }
    }
}
