package zhonghuass.ssml.mvp.ui.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

import VideoHandle.EpEditor;
import VideoHandle.EpVideo;
import VideoHandle.OnEditorListener;
import butterknife.BindView;
import butterknife.OnClick;
import zhonghuass.ssml.R;
import zhonghuass.ssml.di.component.DaggerMediaEditeComponent;
import zhonghuass.ssml.di.module.MediaEditeModule;
import zhonghuass.ssml.mvp.contract.MediaEditeContract;
import zhonghuass.ssml.mvp.model.appbean.VideoEditInfo;
import zhonghuass.ssml.mvp.presenter.MediaEditePresenter;
import zhonghuass.ssml.mvp.ui.MBaseActivity;
import zhonghuass.ssml.mvp.ui.adapter.VideoEditAdapter;
import zhonghuass.ssml.utils.media.EditSpacingItemDecoration;
import zhonghuass.ssml.utils.media.ExtractFrameWorkThread;
import zhonghuass.ssml.utils.media.ExtractVideoInfoUtil;
import zhonghuass.ssml.utils.media.PictureUtils;
import zhonghuass.ssml.utils.media.RangeSeekBar;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MediaEditeActivity extends MBaseActivity<MediaEditePresenter> implements MediaEditeContract.View {

    @BindView(R.id.uVideoView)
    VideoView mVideoView;
    @BindView(R.id.id_rv_id)
    RecyclerView mRecyclerView;
    @BindView(R.id.positionIcon)
    ImageView positionIcon;
    @BindView(R.id.id_seekBarLayout)
    LinearLayout seekBarLayout;
    @BindView(R.id.layout_bottom)
    FrameLayout layoutBottom;
    @BindView(R.id.number_progress_bar)
    NumberProgressBar demoMpc;
    private String path;
    private ExtractVideoInfoUtil mExtractVideoInfoUtil;
    private Long duration;
    private int mMaxWidth;
    private int mScaledTouchSlop;
    private VideoEditAdapter videoEditAdapter;
    private static final long MIN_CUT_DURATION = 3 * 1000L;// 最小剪辑时间3s
    private static final long MAX_CUT_DURATION = 10 * 1000L;//视频最多剪切多长时间
    private static final int MAX_COUNT_RANGE = 12;//seekBar的区域内一共有多少张图片
    private RangeSeekBar seekBar;
    private float averageMsPx;
    private String OutPutFileDirPath;
    private ExtractFrameWorkThread mExtractFrameWorkThread;
    private long leftProgress;
    private long rightProgress;
    private float averagePxMs;
    private long scrollPos = 0;
    private boolean isSeeking;
    private int lastScrollX;
    private List<LocalMedia> selectList;
    private boolean isRuning = false;
    //uiHandler在主线程中创建，所以自动绑定主线程
    private Handler progressHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    System.out.println("msg.arg1:" + msg.arg1);
                    setCurrentProgress(msg.arg1);
                    break;
                case 2:
                    demoMpc.setVisibility(View.GONE);
                    break;
            }
        }
    };

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMediaEditeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mediaEditeModule(new MediaEditeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_media_edite; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("下一步");
        Intent intent = this.getIntent();
        selectList = intent.getParcelableArrayListExtra("mediaList");
        System.out.println("视频地址" + selectList.get(0).getPath());
        initInfo();
        initEditView();
        initEditVideo();
        initPlay();
    }

    @OnClick({R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
               /* //tvRight.setClickable(false);
                if (isRuning) {
                    Toast.makeText(this, "视频正在处理中...", Toast.LENGTH_SHORT).show();
                    return;
                }
                isRuning = true;
                demoMpc.setVisibility(View.VISIBLE);
                getCutMedia();*/
                //发布视频界面
                gotoPostVideos();
                break;

        }
    }

    private void gotoPostVideos() {
        ArmsUtils.startActivity(PostVideosActivity.class);
    }

    private void getCutMedia() {


        if (null == path) {
            Toast.makeText(this, "视频路径错误!", Toast.LENGTH_SHORT).show();
            isRuning = false;
            return;
        }
        File file = new File(path);
        String name = file.getName();

        File filesDir = this.getFilesDir();

        String outFile = filesDir + "/editer" + name;
        File output = new File(outFile);
        if (output.isFile() && output.exists()) {
            System.out.println("删除源文件");
            output.delete();
        }
        System.out.println("存储路径" + outFile);

        EpVideo epVideo = new EpVideo(path);
        EpVideo clip = epVideo.clip((int) (leftProgress / 1000), (int) ((rightProgress - leftProgress) / 1000));
        EpEditor.OutputOption outputOption = new EpEditor.OutputOption(outFile);
       /* outputOption.setWidth(100); //输出视频宽，如果不设置则为原始视频宽高
        outputOption.setHeight(120);//输出视频高度*/
        outputOption.frameRate = 30;//输出视频帧率,默认30
        outputOption.bitRate = 30;//输出视频码率,默认10
        EpEditor.exec(clip, outputOption, new OnEditorListener() {
            @Override
            public void onSuccess() {
                System.out.println("===========");
                isRuning = false;

                Intent intent = new Intent(MediaEditeActivity.this, PublishMediaActivity.class);
                intent.putExtra("mediaPath", outFile);
                startActivity(intent);

                Message message = new Message();
                message.what = 2;

                progressHandler.sendMessage(message);
            }

            @Override
            public void onFailure() {
                isRuning = false;
                Message message = new Message();
                message.what = 2;

                progressHandler.sendMessage(message);

            }

            @Override
            public void onProgress(float v) {
                //   demoMpc.setPercent(random.nextInt(100) / 100f);
                int progress = (int) Math.abs(v * 100);
                Message message = new Message();
                message.what = 1;
                message.arg1 = progress;
                progressHandler.sendMessage(message);

            }


        });
        tvRight.setClickable(true);
    }

    private void setCurrentProgress(int progress) {
        demoMpc.setProgress(progress);
    }

    private void initPlay() {
        mVideoView.setVideoPath(path);
        //设置videoview的OnPrepared监听
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //设置MediaPlayer的OnSeekComplete监听
                mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                    @Override
                    public void onSeekComplete(MediaPlayer mp) {
                        Log.d(TAG, "------ok----real---start-----");
                        Log.d(TAG, "------isSeeking-----" + isSeeking);
                        if (!isSeeking) {
                            videoStart();
                        }
                    }


                });
            }
        });
        //first
        videoStart();
    }

    private void videoStart() {
        Log.d(TAG, "----videoStart----->>>>>>>");
        mVideoView.start();
        positionIcon.clearAnimation();
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
        anim();
        handler.removeCallbacks(run);
        handler.post(run);
    }

    private void initEditVideo() {
        //for video edit
        long startPosition = 0;
        long endPosition = duration;
        int thumbnailsCount;
        int rangeWidth;
        boolean isOver_60_s;
        if (endPosition <= MAX_CUT_DURATION) {
            isOver_60_s = false;
            thumbnailsCount = MAX_COUNT_RANGE;
            rangeWidth = mMaxWidth;
        } else {
            isOver_60_s = true;
            thumbnailsCount = (int) (endPosition * 1.0f / (MAX_CUT_DURATION * 1.0f) * MAX_COUNT_RANGE);
            rangeWidth = mMaxWidth / MAX_COUNT_RANGE * thumbnailsCount;
        }
        mRecyclerView.addItemDecoration(new EditSpacingItemDecoration(ArmsUtils.dip2px(this, 35), thumbnailsCount));

        //init seekBar
        if (isOver_60_s) {
            seekBar = new RangeSeekBar(this, 0L, MAX_CUT_DURATION);
            seekBar.setSelectedMinValue(0L);
            seekBar.setSelectedMaxValue(MAX_CUT_DURATION);
        } else {
            seekBar = new RangeSeekBar(this, 0L, endPosition);
            seekBar.setSelectedMinValue(0L);
            seekBar.setSelectedMaxValue(endPosition);
        }
        seekBar.setMin_cut_time(MIN_CUT_DURATION);//设置最小裁剪时间
        seekBar.setNotifyWhileDragging(true);
        seekBar.setOnRangeSeekBarChangeListener(mOnRangeSeekBarChangeListener);
        seekBarLayout.addView(seekBar);

        Log.d(TAG, "-------thumbnailsCount--->>>>" + thumbnailsCount);
        averageMsPx = duration * 1.0f / rangeWidth * 1.0f;
        Log.d(TAG, "-------rangeWidth--->>>>" + rangeWidth);
        Log.d(TAG, "-------localMedia.getDuration()--->>>>" + duration);
        Log.d(TAG, "-------averageMsPx--->>>>" + averageMsPx);
        OutPutFileDirPath = PictureUtils.getSaveEditThumbnailDir(this);
        int extractW = (ArmsUtils.getScreenWidth(this) - ArmsUtils.dip2px(this, 70)) / MAX_COUNT_RANGE;
        int extractH = ArmsUtils.dip2px(this, 55);
        mExtractFrameWorkThread = new ExtractFrameWorkThread(extractW, extractH, mUIHandler, path, OutPutFileDirPath, startPosition, endPosition, thumbnailsCount);
        mExtractFrameWorkThread.start();

        //init pos icon start
        leftProgress = 0;
        if (isOver_60_s) {
            rightProgress = MAX_CUT_DURATION;
        } else {
            rightProgress = endPosition;
        }
        averagePxMs = (mMaxWidth * 1.0f / (rightProgress - leftProgress));
        Log.d(TAG, "------averagePxMs----:>>>>>" + averagePxMs);


    }

    private final RangeSeekBar.OnRangeSeekBarChangeListener mOnRangeSeekBarChangeListener = new RangeSeekBar.OnRangeSeekBarChangeListener() {


        @Override
        public void onRangeSeekBarValuesChanged(RangeSeekBar bar, long minValue, long maxValue, int action, boolean isMin, RangeSeekBar.Thumb pressedThumb) {
            Log.d(TAG, "-----minValue----->>>>>>" + minValue);
            Log.d(TAG, "-----maxValue----->>>>>>" + maxValue);
            leftProgress = minValue + scrollPos;
            rightProgress = maxValue + scrollPos;
            Log.d(TAG, "-----leftProgress----->>>>>>" + leftProgress);
            Log.d(TAG, "-----rightProgress----->>>>>>" + rightProgress);
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    Log.d(TAG, "-----ACTION_DOWN---->>>>>>");
                    isSeeking = false;
                    videoPause();
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.d(TAG, "-----ACTION_MOVE---->>>>>>");
                    isSeeking = true;
                    mVideoView.seekTo((int) (pressedThumb == RangeSeekBar.Thumb.MIN ?
                            leftProgress : rightProgress));
                    break;
                case MotionEvent.ACTION_UP:
                    Log.d(TAG, "-----ACTION_UP--leftProgress--->>>>>>" + leftProgress);
                    isSeeking = false;
                    //从minValue开始播
                    mVideoView.seekTo((int) leftProgress);
                    //                 videoStart();
                    break;
                default:
                    break;
            }
        }
    };

    private void initEditView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        videoEditAdapter = new VideoEditAdapter(this,
                (ArmsUtils.getScreenWidth(this) - ArmsUtils.dip2px(this, 70)) / 10);
        mRecyclerView.setAdapter(videoEditAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);


    }

    private boolean isOverScaledTouchSlop;
    private final RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.d(TAG, "-------newState:>>>>>" + newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                isSeeking = false;
//                videoStart();
            } else {
                isSeeking = true;
                if (isOverScaledTouchSlop && mVideoView != null && mVideoView.isPlaying()) {
                    videoPause();
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            isSeeking = false;
            int scrollX = getScrollXDistance();
            //达不到滑动的距离
            if (Math.abs(lastScrollX - scrollX) < mScaledTouchSlop) {
                isOverScaledTouchSlop = false;
                return;
            }
            isOverScaledTouchSlop = true;
            Log.d(TAG, "-------scrollX:>>>>>" + scrollX);
            //初始状态,why ? 因为默认的时候有35dp的空白！
            if (scrollX == -ArmsUtils.dip2px(MediaEditeActivity.this, 35)) {
                scrollPos = 0;
            } else {
                // why 在这里处理一下,因为onScrollStateChanged早于onScrolled回调
                if (mVideoView != null && mVideoView.isPlaying()) {
                    videoPause();
                }
                isSeeking = true;
                scrollPos = (long) (averageMsPx * (ArmsUtils.dip2px(MediaEditeActivity.this, 35) + scrollX));
                Log.d(TAG, "-------scrollPos:>>>>>" + scrollPos);
                leftProgress = seekBar.getSelectedMinValue() + scrollPos;
                rightProgress = seekBar.getSelectedMaxValue() + scrollPos;
                Log.d(TAG, "-------leftProgress:>>>>>" + leftProgress);
                mVideoView.seekTo((int) leftProgress);
            }
            lastScrollX = scrollX;
        }
    };

    /**
     * 水平滑动了多少px
     *
     * @return int px
     */
    private int getScrollXDistance() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisibleChildView = layoutManager.findViewByPosition(position);
        int itemWidth = firstVisibleChildView.getWidth();
        return (position) * itemWidth - firstVisibleChildView.getLeft();
    }

    private void initInfo() {
        path = selectList.get(0).getPath();
        //for video check
        if (!new File(path).exists()) {
            Toast.makeText(this, "视频文件不存在", Toast.LENGTH_LONG).show();
            finish();
        }
        mExtractVideoInfoUtil = new ExtractVideoInfoUtil(path);
        duration = Long.valueOf(mExtractVideoInfoUtil.getVideoLength());


        mMaxWidth = ArmsUtils.getScreenWidth(this) - ArmsUtils.dip2px(this, 70);
        mScaledTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();


    }

    private final MainHandler mUIHandler = new MainHandler(this);

    private class MainHandler extends Handler {
        private final WeakReference<MediaEditeActivity> mActivity;

        MainHandler(MediaEditeActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MediaEditeActivity activity = mActivity.get();
            if (activity != null) {
                if (msg.what == ExtractFrameWorkThread.MSG_SAVE_SUCCESS) {
                    if (activity.videoEditAdapter != null) {
                        VideoEditInfo info = (VideoEditInfo) msg.obj;
                        activity.videoEditAdapter.addItemVideoInfo(info);
                    }
                }
            }
        }
    }

    private void videoPause() {
        isSeeking = false;
        if (mVideoView != null && mVideoView.isPlaying()) {
            mVideoView.pause();
            handler.removeCallbacks(run);
        }
        Log.d(TAG, "----videoPause----->>>>>>>");
        if (positionIcon.getVisibility() == View.VISIBLE) {
            positionIcon.setVisibility(View.GONE);
        }
        positionIcon.clearAnimation();
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
    }

    private Handler handler = new Handler();
    private Runnable run = new Runnable() {

        @Override
        public void run() {
            videoProgressUpdate();
            handler.postDelayed(run, 1000);
        }
    };

    private void videoProgressUpdate() {
        long currentPosition = mVideoView.getCurrentPosition();
        Log.d(TAG, "----onProgressUpdate-cp---->>>>>>>" + currentPosition);
        if (currentPosition >= (rightProgress)) {
            mVideoView.seekTo((int) leftProgress);
            positionIcon.clearAnimation();
            if (animator != null && animator.isRunning()) {
                animator.cancel();
            }
            anim();
        }
    }

    private ValueAnimator animator;

    private void anim() {
        Log.d(TAG, "--anim--onProgressUpdate---->>>>>>>" + mVideoView.getCurrentPosition());
        if (positionIcon.getVisibility() == View.GONE) {
            positionIcon.setVisibility(View.VISIBLE);
        }
        final FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) positionIcon.getLayoutParams();
        int start = (int) (ArmsUtils.dip2px(this, 35) + (leftProgress/*mVideoView.getCurrentPosition()*/ - scrollPos) * averagePxMs);
        int end = (int) (ArmsUtils.dip2px(this, 35) + (rightProgress - scrollPos) * averagePxMs);
        animator = ValueAnimator
                .ofInt(start, end)
                .setDuration((rightProgress - scrollPos) - (leftProgress/*mVideoView.getCurrentPosition()*/ - scrollPos));
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.leftMargin = (int) animation.getAnimatedValue();
                System.out.println("positionIcon" + positionIcon);
                System.out.println("params" + params);
                if (null != positionIcon) {

                    positionIcon.setLayoutParams(params);
                }
            }
        });
        animator.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       /* if (animator != null) {
            animator.cancel();
        }*/
        if (mVideoView != null) {
            mVideoView.stopPlayback();
        }
       /* if (mExtractVideoInfoUtil != null) {
            mExtractVideoInfoUtil.release();
        }*/
        //    mRecyclerView.removeOnScrollListener(mOnScrollListener);
        if (mExtractFrameWorkThread != null) {
            mExtractFrameWorkThread.stopExtract();
        }
        mUIHandler.removeCallbacksAndMessages(null);
        handler.removeCallbacksAndMessages(null);
        if (!TextUtils.isEmpty(OutPutFileDirPath)) {
            PictureUtils.deleteFile(new File(OutPutFileDirPath));
        }
       /* if(null!=progressHandler){
            progressHandler=null;
        }*/
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
