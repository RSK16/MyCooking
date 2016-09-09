package com.example.mycooking.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.mycooking.R;
import com.example.mycooking.utils.video.MediaInfor;
import com.example.mycooking.utils.video.NetWorkUtil;
import com.example.mycooking.utils.video.ScreenUtil;
import com.example.mycooking.utils.video.TimerUtil;
import com.example.mycooking.view.VideoInfor;
import com.example.mycooking.view.VideoListAdapter;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

public class VideoActivity extends Activity {
    public static void openMediaPlay(Activity act, String url) {
        Intent intent = new Intent(act, VideoActivity.class);
        act.startActivity(intent);
    }

    //= http://download.yxybb.com/bbvideo/web/d1/d13/d2/d1/f1-web.mp4
    //private String path = "http://10.0.2.2:8080/aa.mp4";
    private String path =
            "http://bmob-cdn-6039.b0.upaiyun.com/2016/09/08/5b26c8fc4058564a80bc21aa802f246e.mp4";

    private VideoView mVideoView;
    private MediaPlayer mMediaPlayer;

    public static final String TAG = "SJZ";

    private static final int MSG_SURFACE_PREPARE = 0x00000000;
    private static final int MSG_SURFACE_START = 0x00000001;
    private static final int MSG_SURFACE_DESTORY = 0x00000003;

    private static final int MSG_SCREEN_FULL = 0x00000004;
    private static final int MSG_SCREEN_WRAP = 0x00000005;

    private static final int MSG_UPDATE_PROGRESS = 0x00000006;
    private static final int MSG_MEDIA_CONTROLLER_HIDE = 0x00000007;

    private static final String KEY_CURRENT_POSITION = "KEY_CURRENT_POSITION";

    private RelativeLayout mTop_Controller;
    private RelativeLayout mBotton_Controller;
    private RelativeLayout mRela_Layout;
    private LinearLayout mLinear_Full_Wrap;
    private ImageView mImage_Full_Screen;
    private ImageView mImage_Back;
    private TextView mText_Current;
    private TextView mText_Durtion;
    private SeekBar mSeekBar;
    private ProgressBar mPro_Buffer;
    private ImageView mImage_PlayOrPause;
    private TextView mText_Title;


    private Timer mServerTimer = null;
    private Timer mControllerTimer = null;

    private int mResumePostion = 0;
    private int mDuration = -1;
    private boolean mIsHandPause = false;

    private int mCurrentState = 0;

    /**
     * 由于mediaplay为异步加载，防止程序进入后台后播放
     * onPause()时，记录当前播放状态，重新onResume()恢复之前状态
     * mIsPrepred 参数为onPause()判断当前是否初始化完成
     */
    private boolean mIsOnPauseStatus = false;  //记录onPause()之前的播放状态 播放或者暂停
    private boolean mIsPrepred = false;        //记录onPause()之前 视频是否初始化完成
    private boolean mIsBackPrepared = false;           //由于mediaplay是异步加载，当home时可能会在后台播放的可能

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SURFACE_PREPARE:
                    MediaInfor mediaInfor = (MediaInfor) msg.obj;
                    setDataSource(mediaInfor.mPath, mediaInfor.mName);
                    break;

                case MSG_SURFACE_START:
                    mIsPrepred = true;
                    mPro_Buffer.setVisibility(View.GONE);
                    mImage_PlayOrPause.setImageResource(R.drawable.jc_click_pause_selector);
                    mImage_PlayOrPause.setVisibility(View.VISIBLE);
                    mVideoView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    mVideoView.start();
                    startProgressTimer();
                    startControllerShowOrHide();
                    break;

                case MSG_SURFACE_DESTORY:
                    if (mBotton_Controller.getVisibility() == View.VISIBLE) {
                        mediaControllerHide();
                    } else {
                        mediaControllerShow();
                    }
                    break;

                case MSG_SCREEN_FULL:
                    screenFullModeUI();
                    break;

                case MSG_SCREEN_WRAP:
                    screenWrapModeUI();
                    break;

                case MSG_UPDATE_PROGRESS:
                    setProgressController(0);
                    break;

                case MSG_MEDIA_CONTROLLER_HIDE:
                    mediaControllerHide();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video);
        init();
        initMediaController();
        initBottomLayout();
        resetProgressAndTimer();
    }

    private void initMediaController() {


        mImage_PlayOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    mImage_PlayOrPause.setImageResource(R.drawable.jc_click_play_selector);
                    mIsHandPause = true;
                } else {
                    mVideoView.start();
                    mImage_PlayOrPause.setImageResource(R.drawable.jc_click_pause_selector);
                    mIsHandPause = false;
                }
            }
        });
        mLinear_Full_Wrap = (LinearLayout) findViewById(R.id.linear_full_or_wrap);
        mLinear_Full_Wrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getScreenOrientation() == Configuration.ORIENTATION_PORTRAIT) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    return;
                }
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        });
        mImage_Back = (ImageView) findViewById(R.id.image_back);
        mImage_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getScreenOrientation() == Configuration.ORIENTATION_PORTRAIT) {
                    finish();
                    return;
                }
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        });
        mSeekBar = (SeekBar) findViewById(R.id.progress);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                try {
//                    int time = progress * getDuration() / 100;
//                    mMediaPlayer.seekTo(time);
//                } catch (IllegalStateException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        final ImageView imageFristPlay = (ImageView) findViewById(R.id.image_frist_play);
        imageFristPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!NetWorkUtil.isNetWorkEnable(VideoActivity.this)){
                    Toast.makeText(VideoActivity.this, getString(R.string.network_is_unable), Toast.LENGTH_SHORT).show();
                    return;
                }
                imageFristPlay.setVisibility(View.GONE);
                playMedia(path, "课后练习题");
            }
        });
    }

    private void init() {
        mVideoView = (VideoView) findViewById(R.id.surfaceview);

        mTop_Controller = (RelativeLayout) findViewById(R.id.top_media_controller);
        mBotton_Controller = (RelativeLayout) findViewById(R.id.bottom_media_controller);
        mText_Current = (TextView) findViewById(R.id.text_currentpostion);
        mText_Durtion = (TextView) findViewById(R.id.text_durtionposition);
        mImage_Full_Screen = (ImageView) findViewById(R.id.image_full_screen);
        mPro_Buffer = (ProgressBar) findViewById(R.id.image_buffer);
        mText_Title = (TextView) findViewById(R.id.text_title);
        mRela_Layout = (RelativeLayout) findViewById(R.id.layout_bottom);
        mImage_PlayOrPause = (ImageView) findViewById(R.id.image_playorpause);
    }

    private MediaPlayer.OnErrorListener mOnErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            return false;
        }
    };

    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            if (mp.getDuration() != -1 && mp.getCurrentPosition() >= mp.getDuration() - 1000) {
                cancleControllerTimer();
                mImage_PlayOrPause.setImageResource(R.drawable.jc_click_play_selector);
                mediaControllerShow();
                Toast.makeText(VideoActivity.this, "播放完成", Toast.LENGTH_LONG).show();
            }

        }
    };

    private MediaPlayer.OnInfoListener mOnInfoListener = new MediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            switch (what) {
                case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                    mPro_Buffer.setVisibility(View.VISIBLE);
                    if (mImage_PlayOrPause.getVisibility() == View.VISIBLE) {
                        mImage_PlayOrPause.setVisibility(View.GONE);
                    }
                    break;

                case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                    if (mVideoView.isPlaying() || mIsHandPause) {
                        mPro_Buffer.setVisibility(View.GONE);
                        mImage_PlayOrPause.setVisibility(mBotton_Controller.getVisibility() == View.VISIBLE ? View.VISIBLE : View.GONE);
                    }
                    break;

                default:
                    break;
            }
            return true;
        }
    };

    private MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            if (mIsBackPrepared) {
                mVideoView.pause();
                mIsBackPrepared = false;
            } else {
                mHandler.sendEmptyMessage(MSG_SURFACE_START);
            }
            if (mMediaPlayer == null) {
                mMediaPlayer = mp;
                mMediaPlayer.setOnInfoListener(mOnInfoListener);
                mMediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
            }
        }

    };

    private MediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            if (mServerTimer != null && percent > 0) {
                setProgressController(percent);
            }
        }
    };

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mHandler.sendEmptyMessage(MSG_SURFACE_DESTORY);
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    private void setListaner() {
        mVideoView.setOnTouchListener(mOnTouchListener);
        mVideoView.setOnTouchListener(mOnTouchListener);
        mVideoView.setOnErrorListener(mOnErrorListener);
        mVideoView.setOnCompletionListener(mOnCompletionListener);
        mVideoView.setOnPreparedListener(mOnPreparedListener);
    }

    public void playMedia(String path, String name) {
        if (!NetWorkUtil.isNetWorkEnable(this)) {
            Toast.makeText(this, R.string.network_is_unable, Toast.LENGTH_SHORT).show();
            return;
        }
        Message msg = Message.obtain();
        MediaInfor mediaInfor = new MediaInfor();
        mediaInfor.mName = name;
        mediaInfor.mPath = path;
        msg.obj = mediaInfor;
        msg.what = MSG_SURFACE_PREPARE;
        mHandler.sendMessage(msg);
    }

    public void setDataSource(String path, String name) {
        if (TextUtils.isEmpty(path)) return;
        mPro_Buffer.setVisibility(View.VISIBLE);
        mImage_PlayOrPause.setVisibility(View.GONE);
        resetProgressAndTimer();
        mVideoView.requestFocus();
        mVideoView.setBackgroundColor(getResources().getColor(R.color.videobackcolor));
        try {
            setListaner();
            File localFile = new File(path);
            if (localFile.isFile()) {
                mVideoView.setVideoURI(Uri.fromFile(localFile));
            } else {
                mVideoView.setVideoPath(path);
            }
            mVideoView.start();
            mText_Title.setText(name);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int value = getScreenOrientation();
        if (value == Configuration.ORIENTATION_LANDSCAPE) {
            mHandler.sendEmptyMessage(MSG_SCREEN_FULL);
        } else if (value == Configuration.ORIENTATION_PORTRAIT) {
            mHandler.sendEmptyMessage(MSG_SCREEN_WRAP);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getScreenOrientation() == Configuration.ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private int getMediaCurrentPostion() {
        if (mVideoView != null) {
            return mVideoView.getCurrentPosition();
        }
        return 0;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_POSITION, getMediaCurrentPostion());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mResumePostion = savedInstanceState.getInt(KEY_CURRENT_POSITION, 0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsBackPrepared = false;
        try {
            if (mVideoView != null) {
                mVideoView.seekTo(mResumePostion > 0 ? mResumePostion : 0);
                Log.e(TAG, "onResume:");
                mVideoView.start();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        getCurrentState();

    }

    private void getCurrentState() {
        Field field;
        try {
            field = VideoView.class.getDeclaredField("mCurrentState");
            field.setAccessible(true);
            mCurrentState = field.getInt(mVideoView);
            Log.e(TAG, "mCurrentState: " + mCurrentState);
            if (mCurrentState == 0) {

            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsBackPrepared = true;
        mIsOnPauseStatus = mVideoView.isPlaying();
        try {
            if (mVideoView != null) {
                mVideoView.pause();
                mIsHandPause = true;
                mResumePostion = getMediaCurrentPostion();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView = null;
        }
        cancleControllerTimer();
    }

    public void screenFullModeUI() {
        mRela_Layout.setVisibility(View.GONE);
        mImage_Full_Screen.setImageResource(R.drawable.jc_shrink);
        setScreenLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public void screenWrapModeUI() {
        mRela_Layout.setVisibility(View.VISIBLE);
        mImage_Full_Screen.setImageResource(R.drawable.jc_enlarge);
        setScreenLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.dip2px(this, 220));
    }

    private int getScreenOrientation() {
        return getResources().getConfiguration().orientation;
    }

    private void setScreenLayoutParams(int width, int height) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        if (width == ViewGroup.LayoutParams.MATCH_PARENT && height == ViewGroup.LayoutParams.MATCH_PARENT) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
        mVideoView.setLayoutParams(layoutParams);
        mVideoView.requestFocus();
    }

    private void setProgressAndTime(int current, int duration, int progress, int secProgress) {
        mSeekBar.setProgress(progress > 0 ? progress : 0);
        if (secProgress > 0) {
            mSeekBar.setSecondaryProgress(secProgress);
        }
        mText_Current.setText(TimerUtil.stringForTime(current));
        mText_Durtion.setText(TimerUtil.stringForTime(duration));
    }

    private void mediaControllerHide() {
        mTop_Controller.setVisibility(View.GONE);
        mBotton_Controller.setVisibility(View.GONE);
        mImage_PlayOrPause.setVisibility(View.GONE);
        cancleControllerTimer();
    }

    private void mediaControllerShow() {
        cancleControllerTimer();
        mTop_Controller.setVisibility(View.VISIBLE);
        mBotton_Controller.setVisibility(View.VISIBLE);
        mImage_PlayOrPause.setVisibility(mPro_Buffer.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        mImage_PlayOrPause.setImageResource(mVideoView.isPlaying() ? R.drawable.jc_click_pause_selector : R.drawable.jc_click_play_selector);
        startProgressTimer();
        startControllerShowOrHide();
    }

    public int getDuration() {
        int du = mVideoView.getDuration();
        int duration = du != -1 ? du : mDuration;  //home键恢复后 有可能拿不到总长度值 故
        return duration;
    }

    private void resetProgressAndTimer() {
        mDuration = 0;
        mSeekBar.setProgress(0);
        mSeekBar.setSecondaryProgress(0);
        mText_Current.setText("00:00");
        mText_Durtion.setText("00:00");
    }

    private void startProgressTimer() {
        cancleControllerTimer();
        mServerTimer = new Timer();
        mServerTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    mHandler.sendEmptyMessage(MSG_UPDATE_PROGRESS);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 500);
    }

    private void startControllerShowOrHide() {
        mControllerTimer = new Timer();
        mControllerTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(MSG_MEDIA_CONTROLLER_HIDE);
            }
        }, 3000, 1);
    }

    private void cancleControllerTimer() {
        if (mServerTimer != null) {
            mServerTimer.cancel();
            mServerTimer = null;
        }
        if (mControllerTimer != null) {
            mControllerTimer.cancel();
            mControllerTimer = null;
        }
    }

    private void setProgressController(int percent) {
        int currentPostion = 0;
        int duration = -1;
        try {
            currentPostion = mVideoView.getCurrentPosition();
            duration = getDuration();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        int progress = currentPostion * 100 / (duration == 0 ? 1 : duration);
        setProgressAndTime(currentPostion, duration, progress, percent);
    }

    private void initBottomLayout() {
        ListView listVideo = (ListView) findViewById(R.id.list_video);
        final VideoListAdapter adapter = new VideoListAdapter(this);
        listVideo.setAdapter(adapter);
        adapter.setData(null);
        listVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoInfor infor = (VideoInfor) adapter.getItem(position);
                playMedia(infor.videoPath, infor.videoTitle);
            }
        });

    }
}
