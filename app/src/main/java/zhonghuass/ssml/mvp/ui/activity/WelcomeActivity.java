package zhonghuass.ssml.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

import zhonghuass.ssml.R;
import zhonghuass.ssml.utils.PrefUtils;

public class WelcomeActivity extends Activity {

    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_welcome);

        RelativeLayout layoutSplash = findViewById(R.id.rl_splash);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation.setDuration(500);//设置动画播放时长1000毫秒（1秒）
        layoutSplash.startAnimation(alphaAnimation);
        handler = new Handler();
        handler.postDelayed(new StartHandler(), 2000);
    }

    private class StartHandler implements Runnable {
        @Override
        public void run() {
            boolean isFirstIn = PrefUtils.getBoolean(
                    getApplicationContext(), "isFirstIn", true);

            if (isFirstIn) {
                // 跳转新手引导界面s
                Intent intent = new Intent();
                intent.setClass(WelcomeActivity.this, GuideActivity.class);
                startActivity(intent);
            } else {
                startActivity(new Intent(WelcomeActivity.this, LogInActivity.class));
            }
            finish();
        }
    }
}
