package zhonghuass.ssml.utils.image;

import android.view.MotionEvent;

interface OnSingleFlingListener {
    boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);
}
