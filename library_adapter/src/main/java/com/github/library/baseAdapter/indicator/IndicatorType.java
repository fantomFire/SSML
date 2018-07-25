package com.github.library.baseAdapter.indicator;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.github.library.baseAdapter.indicator.BaseIndicator.CANCEL_STATUS;
import static com.github.library.baseAdapter.indicator.BaseIndicator.END_STATUS;
import static com.github.library.baseAdapter.indicator.BaseIndicator.START_STATUS;


/**
 * Created by boby on 2016/12/11.
 */
@IntDef({START_STATUS, END_STATUS, CANCEL_STATUS})
@Retention(RetentionPolicy.RUNTIME)
public @interface IndicatorType {
}
