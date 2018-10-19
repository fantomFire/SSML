package zhonghuass.ssml.utils;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.bumptech.glide.request.target.ViewTarget;
import com.jess.arms.base.BaseApplication;

import zhonghuass.ssml.R;

public class MyApp  extends BaseApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        ViewTarget.setTagId(R.id.tag_glide);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
