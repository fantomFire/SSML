package zhonghuass.ssml.mvp.ui.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerMediaPlayerComponent;
import zhonghuass.ssml.di.module.MediaPlayerModule;
import zhonghuass.ssml.mvp.contract.MediaPlayerContract;
import zhonghuass.ssml.mvp.presenter.MediaPlayerPresenter;


public class MediaPlayerActivity extends BaseActivity<MediaPlayerPresenter> implements MediaPlayerContract.View {

    @BindView(R.id.media_view)
    VideoView mediaView;
    Uri playerUri = Uri.parse("http://mp4.vjshi.com/2018-08-24/d5c38d9ba8f01df0deaf9c2be1bfd377.mp4");
    @BindView(R.id.rl_content)
    RelativeLayout rlContent;
    private String path = "/data/data/zhonghuass.ssml/files/editerQQ视频_4EF3A4822509623660D20F12D677317A.mp4";
    private String path1 = "http://mp4.vjshi.com/2018-08-24/d5c38d9ba8f01df0deaf9c2be1bfd377.mp4";
    @BindView(R.id.btnPlay)
    ImageView btnPlay;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMediaPlayerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mediaPlayerModule(new MediaPlayerModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_media_player; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        String mediaPath = getIntent().getStringExtra("mediaPath");
        if(null!=mediaPath){

            mediaView.setVideoPath(mediaPath);
        }

        mediaView.setMediaController(new MediaController(this));
        mediaView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);

            }
        });
        rlContent.setOnTouchListener(new View.OnTouchListener() {

            private float currentY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        currentY = event.getRawY();
                        System.out.println("wwwwww"+currentY);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        float rawY = event.getRawY();
                        int v1 = (int) (rawY - currentY);
                        if(Math.abs(v1)>50){
                            System.out.println("===="+v1);
                            if(v1>0){
                                mediaView.setVideoPath(path1);

                            }else {
                                mediaView.setVideoPath(path);
                            }
                            btnPlay.setVisibility(View.VISIBLE);
                        }
                        break;

                }
                return true;
            }
        });

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @OnClick({R.id.btnPlay, R.id.rl_content})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnPlay:
                if (mediaView.isPlaying()) {
                    mediaView.pause();
                } else {
                    mediaView.start();
                    btnPlay.setVisibility(View.GONE);
                }
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaView != null){
            mediaView.suspend();
        }
    }
}
