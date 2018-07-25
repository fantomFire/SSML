package zhonghuass.ssml.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import zhonghuass.ssml.R;
import zhonghuass.ssml.utils.PrefUtils;

public class GuideActivity extends Activity {
    private int[] mResIds = {R.mipmap.yindao1, R.mipmap.yindao2, R.mipmap.yindao3};
    private TextView btnStart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPager.setAdapter(new MyAdapter());

    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mResIds.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView iv = new ImageView(getApplicationContext());
            iv.setBackgroundResource(mResIds[position]);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position == 2) {
                        PrefUtils.putBoolean(getApplicationContext(), "isFirstIn",
                                false);
                        Intent intent = new Intent();
                        intent.setClass(GuideActivity.this, MainActivity.class);
                        startActivity(intent);

                        finish();
                    }
                }
            });
            container.addView(iv);

            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


    }
}
