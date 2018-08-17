package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.dl7.recycler.adapter.BaseMultiItemQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerChattingComponent;
import zhonghuass.ssml.di.module.ChattingModule;
import zhonghuass.ssml.mvp.contract.ChattingContract;
import zhonghuass.ssml.mvp.model.appbean.ChatBean;
import zhonghuass.ssml.mvp.presenter.ChattingPresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.mvp.ui.adapter.ChattingAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ChattingActivity extends MBaseActivity<ChattingPresenter> implements ChattingContract.View {

    @BindView(R.id.rv_sms)
    RecyclerView rvSms;
    @BindView(R.id.btn_add_emoji)
    ImageView btnAddEmoji;
    @BindView(R.id.edtSms)
    EditText edtSms;
    @BindView(R.id.btn_add_photo)
    ImageView btnAddPhoto;
    @BindView(R.id.btnSms)
    Button btnSms;
    private List<ChatBean> mList=new ArrayList<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChattingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .chattingModule(new ChattingModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_chatting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new ChattingAdapter(this,mList);
        initToolBar("");

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
}
